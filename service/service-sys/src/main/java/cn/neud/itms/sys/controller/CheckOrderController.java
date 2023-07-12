package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.CheckOrder;
import cn.neud.itms.sys.service.CheckOrderService;
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
@Api(tags = "验货单管理")
@RestController
@RequestMapping("/admin/sys/checkOrder")
//@CrossOrigin
public class CheckOrderController {

    @Autowired
    private CheckOrderService checkOrderService;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("任务单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody CheckOrder checkOrder
    ) {
        Page<CheckOrder> pageParam = new Page<>(page, limit);
        IPage<CheckOrder> pageModel = checkOrderService.selectPage(pageParam, checkOrder);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        CheckOrder checkOrder = checkOrderService.getById(id);
        return Result.ok(checkOrder);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody CheckOrder checkOrder) {
        checkOrderService.save(checkOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody CheckOrder checkOrder) {
        checkOrderService.updateById(checkOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        checkOrderService.removeById(id);
        return Result.ok(null);
    }

}

