package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.sys.service.PurchaseOrderService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "购货单管理")
@RestController
@RequestMapping("/admin/sys/purchaseOrder")
//@CrossOrigin
public class PurchaseOrderController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("任务单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody PurchaseOrder purchaseOrder
    ) {
        Page<PurchaseOrder> pageParam = new Page<>(page, limit);
        IPage<PurchaseOrder> pageModel = purchaseOrderService.selectPage(pageParam, purchaseOrder);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        PurchaseOrder purchaseOrder = purchaseOrderService.getById(id);
        return Result.ok(purchaseOrder);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrderService.save(purchaseOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrderService.updateById(purchaseOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        purchaseOrderService.removeById(id);
        return Result.ok(null);
    }

}

