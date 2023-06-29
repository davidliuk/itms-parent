package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.RegionStation;
import cn.neud.itms.sys.service.RegionStationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "分站管理接口")
@RestController
@RequestMapping("/admin/sys/station")
//@CrossOrigin
public class RegionStationController {

    @Autowired
    private RegionStationService regionStationService;

    //根据区域关键字查询区域列表信息
//    url: `${api_name}/findRegionStationByKeyword/${keyword}`,
//    method: 'get'
    @ApiOperation("根据分站关键字查询区域列表信息")
    @GetMapping("findRegionStationByKeyword/{keyword}")
    public Result findRegionStationByKeyword(@PathVariable("keyword") String keyword) {
        List<RegionStation> list = regionStationService.getRegionStationByKeyword(keyword);
        return Result.ok(list);
    }

    @ApiOperation("根据平台属性分组id查询")
    @GetMapping("{regionId}")
    public Result list(@PathVariable Long regionId) {
        List<RegionStation> list = regionStationService.getRegionStationListByGroupId(regionId);
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("get/{id}")
    public Result get(@PathVariable Long id) {
        RegionStation region = regionStationService.getById(id);
        return Result.ok(region);
    }

    @ApiOperation(value = "新增")
    @PostMapping("save")
    public Result save(@RequestBody RegionStation region) {
        regionStationService.save(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("update")
    public Result updateById(@RequestBody RegionStation region) {
        regionStationService.updateById(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("remove/{id}")
    public Result remove(@PathVariable Long id) {
        regionStationService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<Long> idList) {
        regionStationService.removeByIds(idList);
        return Result.ok(null);
    }

}

