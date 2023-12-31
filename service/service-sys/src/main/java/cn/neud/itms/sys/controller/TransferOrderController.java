package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.TransferOrder;
import cn.neud.itms.sys.service.TransferOrderService;
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
@Api(tags = "调拨单管理")
@RestController
@RequestMapping("/admin/sys/transferOrder")
//@CrossOrigin
public class TransferOrderController {

    @Autowired
    private TransferOrderService transferOrderService;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("调拨单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody TransferOrder transferOrder
    ) {
        Page<TransferOrder> pageParam = new Page<>(page, limit);
        IPage<TransferOrder> pageModel = transferOrderService.selectPage(pageParam, transferOrder);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        TransferOrder transferOrder = transferOrderService.getById(id);
        return Result.ok(transferOrder);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody TransferOrder transferOrder) {
        transferOrderService.save(transferOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody TransferOrder transferOrder) {
        transferOrderService.updateById(transferOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        transferOrderService.removeById(id);
        return Result.ok(null);
    }

}

