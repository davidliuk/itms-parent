package cn.neud.itms.sys.controller;


import cn.hutool.log.Log;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.sys.service.LogisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "运输公司管理")
@RestController
@RequestMapping("/admin/sys/logistics")
//@CrossOrigin
public class LogisticsController {

    @Autowired
    private LogisticsService logisticsService;

    @ApiOperation(value = "根据仓库ID获取")
    @GetMapping("getByWareId/{wareId}")
    public Result getByWareId(@PathVariable Long wareId) {
        List<Logistics> logistics = logisticsService.list(new LambdaQueryWrapper<Logistics>()
                .eq(Logistics::getWareId, wareId));
        return Result.ok(logistics);
    }

    @ApiOperation("任务单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody Logistics logistics
    ) {
        Page<Logistics> pageParam = new Page<>(page, limit);
        IPage<Logistics> pageModel = logisticsService.selectPage(pageParam, logistics);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Logistics logistics = logisticsService.getById(id);
        return Result.ok(logistics);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Logistics logistics) {
        logisticsService.save(logistics);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Logistics logistics) {
        logisticsService.updateById(logistics);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        logisticsService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "批量删除")
    @DeleteMapping("")
    public Result remove(@RequestBody List<Long> ids) {
        logisticsService.removeByIds(ids);
        return Result.ok(null);
    }

}

