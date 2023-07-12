package cn.neud.itms.product.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.product.Supplier;
import cn.neud.itms.product.service.SupplierService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 仓库表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "供应商管理")
@RestController
@RequestMapping("/admin/product/supplier")
//@CrossOrigin
public class SupplierController {

    @Autowired
    private SupplierService supplierService;

    //查询所有仓库列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation("供应商库存列表")
    @PostMapping("{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody Supplier skuWare
    ) {
        Page<Supplier> pageParam = new Page<>(page, limit);
        IPage<Supplier> pageModel = supplierService.selectPage(pageParam, skuWare);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Supplier supplier = supplierService.getById(id);
        return Result.ok(supplier);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Supplier supplier) {
        supplierService.save(supplier);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Supplier supplier) {
        supplierService.updateById(supplier);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        supplierService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        supplierService.removeByIds(idList);
        return Result.ok(null);
    }

}

