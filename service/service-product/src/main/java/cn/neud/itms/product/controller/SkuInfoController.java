package cn.neud.itms.product.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.model.product.SkuWare;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.product.service.SkuInfoService;
import cn.neud.itms.product.service.SkuWareService;
import cn.neud.itms.sys.client.SysFeignClient;
import cn.neud.itms.vo.product.SkuInfoQueryVo;
import cn.neud.itms.vo.product.SkuInfoVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@Api(tags = "商品信息管理")
@RestController
@RequestMapping("/admin/product/skuInfo")
//@CrossOrigin
public class SkuInfoController {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private SkuWareService skuWareService;

    @Autowired
    private SysFeignClient sysFeignClient;

    //sku列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("sku列表")
    @PostMapping("{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            SkuInfoQueryVo skuInfoQueryVo
    ) {
        Page<SkuInfo> pageParam = new Page<>(page, limit);
        IPage<SkuInfo> pageModel = skuInfoService.selectPageSkuInfo(pageParam, skuInfoQueryVo);
        return Result.ok(pageModel);
    }

    //添加商品sku信息
//    url: `${api_name}/save`,
//    method: 'post',
//    data: role
    @ApiOperation("添加商品sku信息")
    @PostMapping("")
    public Result save(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.saveSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation("添加商品sku库存")
    @PostMapping("stock")
    public Result stock(@RequestBody SkuWare skuWare) {
        if (skuWareService.count(new LambdaQueryWrapper<SkuWare>()
                .eq(SkuWare::getSkuId, skuWare.getSkuId())
                .eq(SkuWare::getWareId, skuWare.getWareId())) > 0) {
//            return Result.fail("该仓库已经存在该商品库存");
            skuInfoService.addStock(skuWare.getWareId(), skuWare.getSkuId(), skuWare.getStock());
        } else {
            skuWareService.save(skuWare);
        }
        SkuInfo skuInfo = skuInfoService.getById(skuWare.getSkuId());
        PurchaseOrder purchaseOrder = new PurchaseOrder();
        BeanUtils.copyProperties(skuWare, purchaseOrder);
        BeanUtils.copyProperties(skuInfo, purchaseOrder);
        sysFeignClient.savePurchaseOrder(purchaseOrder);
        return Result.ok(null);
    }

    //    url: `${api_name}/get/${id}`,
//    method: 'get'
    @ApiOperation("获取sku信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    //    url: `${api_name}/update`,
//    method: 'put',
//    data: role
    @ApiOperation("修改sku")
    @PutMapping("")
    public Result update(@RequestBody SkuInfoVo skuInfoVo) {
        skuInfoService.updateSkuInfo(skuInfoVo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        skuInfoService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        skuInfoService.removeByIds(idList);
        return Result.ok(null);
    }

    //    url: `${api_name}/check/${id}/${status}`,
//    method: 'get'
    @ApiOperation("商品审核")
    @GetMapping("check/{skuId}/{status}")
    public Result check(@PathVariable Long skuId,
                        @PathVariable Integer status) {
        skuInfoService.check(skuId, status);
        return Result.ok(null);
    }

    //    url: `${api_name}/publish/${id}/${status}`,
//    method: 'get'
    @ApiOperation("商品上下架")
    @GetMapping("publish/{skuId}/{status}")
    public Result publish(@PathVariable Long skuId,
                          @PathVariable Integer status) {
        skuInfoService.publish(skuId, status);
        return Result.ok(null);
    }

    // 新人专享
    @GetMapping("isNewPerson/{skuId}/{status}")
    public Result isNewPerson(@PathVariable("skuId") Long skuId,
                              @PathVariable("status") Integer status) {
        skuInfoService.isNewPerson(skuId, status);
        return Result.ok(null);
    }
}

