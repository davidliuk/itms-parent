package cn.neud.itms.product.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.PurchaseStatus;
import cn.neud.itms.enums.StorageType;
import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.model.product.SkuWare;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.product.service.SkuInfoService;
import cn.neud.itms.product.service.SkuWareService;
import cn.neud.itms.sys.client.SysFeignClient;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Api(tags = "商品库存管理")
@RestController
@RequestMapping("/admin/product/skuWare")
//@CrossOrigin
public class SkuWareController {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuWareService skuWareService;

    @Autowired
    private SysFeignClient sysFeignClient;

    @ApiOperation("sku库存列表")
    @PostMapping("{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody SkuWare skuWare
    ) {
        Page<SkuWare> pageParam = new Page<>(page, limit);
        IPage<SkuWare> pageModel = skuWareService.selectPage(pageParam, skuWare);
        return Result.ok(pageModel);
    }

    @ApiOperation("根据wareId获取所有sku库存列表")
    @GetMapping("getByWareId/{wareId}")
    public Result getByWareId(
            @PathVariable Long wareId
    ) {
        List<SkuWare> skuWares = skuWareService.list(new LambdaQueryWrapper<SkuWare>()
                        .eq(SkuWare::getWareId, wareId)).stream()
                .peek(skuWare -> skuWare.setSkuInfo(skuInfoService.getById(skuWare.getSkuId())))
                .collect(Collectors.toList());
        return Result.ok(skuWares);
    }

    @ApiOperation("仓库库存求和")
    @PostMapping("/sum")
    public Result selectStockByIds(
            @RequestBody Long[] ids
    ) {
        return Result.ok(skuWareService.selectStockByIds(ids));
    }

    @ApiOperation("进货")
    @PostMapping("stock")
    public Result stock(@RequestBody SkuWare skuWare) {
        SkuInfo skuInfo = skuInfoService.getById(skuWare.getSkuId());
        if (skuInfo == null) {
            return Result.fail("该商品不存在");
        }
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        BeanUtils.copyProperties(skuWare, purchaseOrder);
        BeanUtils.copyProperties(skuInfo, purchaseOrder);
        purchaseOrder.setStatus(PurchaseStatus.PAID);
        sysFeignClient.savePurchaseOrder(purchaseOrder);
        StorageOrder storageOrder = new StorageOrder();
        BeanUtils.copyProperties(skuWare, storageOrder);
        BeanUtils.copyProperties(skuInfo, storageOrder);
        sysFeignClient.saveStorageOrder(storageOrder);
        return Result.ok(null);
    }

    @ApiOperation("进货入库")
    @GetMapping("stockIn/{purchaseOrderId}")
    public Result stockIn(@PathVariable Long purchaseOrderId) {
        PurchaseOrder purchaseOrder = sysFeignClient.getPurchaseOrder(purchaseOrderId);
        if (purchaseOrder == null) {
            return Result.fail("购货单不存在");
        }
        if (purchaseOrder.getStatus() != PurchaseStatus.PAID) {
            return Result.fail("购货单状态错误");
        }
        SkuWare skuWare = new SkuWare();
        BeanUtils.copyProperties(purchaseOrder, skuWare);
        if (skuWareService.count(new LambdaQueryWrapper<SkuWare>()
                .eq(SkuWare::getSkuId, skuWare.getSkuId())
                .eq(SkuWare::getWareId, skuWare.getWareId())) > 0) {
//            return Result.fail("该仓库已经存在该商品库存");
            skuInfoService.addStock(skuWare.getWareId(), skuWare.getSkuId(), skuWare.getStock());
        } else {
            skuWareService.save(skuWare);
        }
        purchaseOrder.setStatus(PurchaseStatus.IN);
        sysFeignClient.updatePurchaseOrder(purchaseOrder);
        return Result.ok(null);
    }

    @ApiOperation("退货出库供应商")
    @PostMapping("returnStock")
    public Result returnStock(@RequestBody SkuWare skuWare) {
        SkuWare one = skuWareService.getOne(new LambdaQueryWrapper<SkuWare>()
                .eq(SkuWare::getSkuId, skuWare.getSkuId())
                .eq(SkuWare::getWareId, skuWare.getWareId()));
        if (one == null) {
            return Result.fail("该仓库不存在该商品库存");
        }
        if (one.getStock() < skuWare.getStock()) {
            return Result.fail("该仓库不存在足够库存的商品");
        }
        skuInfoService.addStock(skuWare.getWareId(), skuWare.getSkuId(), -skuWare.getStock());
        SkuInfo skuInfo = skuInfoService.getById(skuWare.getSkuId());
        StorageOrder storageOrder = new StorageOrder();
        storageOrder.setStorageType(StorageType.RETURN_SUPPLIER);
        BeanUtils.copyProperties(skuWare, storageOrder);
        BeanUtils.copyProperties(skuInfo, storageOrder);
        sysFeignClient.saveStorageOrder(storageOrder);
        return Result.ok(null);
    }

}
