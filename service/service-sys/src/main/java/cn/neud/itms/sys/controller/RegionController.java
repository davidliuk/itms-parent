package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.Region;
import cn.neud.itms.sys.service.RegionService;
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
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "区域管理接口")
@RestController
@RequestMapping("/admin/sys/region")
//@CrossOrigin
public class RegionController {

    @Autowired
    private RegionService regionService;

    //根据区域关键字查询区域列表信息
//    url: `${api_name}/findRegionByKeyword/${keyword}`,
//    method: 'get'
    @ApiOperation("根据区域关键字查询区域列表信息")
    @GetMapping("findRegionByKeyword/{keyword}")
    public Result findRegionByKeyword(@PathVariable("keyword") String keyword) {
        List<Region> list = regionService.getRegionByKeyword(keyword);
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("assign")
    public Result assign(
            @RequestParam Long regionId,
            @RequestParam Long[] stationId
    ) {
//        Region region = regionService.getById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Region region = regionService.getById(id);
        return Result.ok(region);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Region region) {
        regionService.save(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Region region) {
        regionService.updateById(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        regionService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        regionService.removeByIds(idList);
        return Result.ok(null);
    }

}

