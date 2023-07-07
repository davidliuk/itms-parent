package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.StorageType;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.model.sys.*;
import cn.neud.itms.sys.service.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private StorageOrderService storageOrderService;

    @Autowired
    private CheckOrderService checkOrderService;

    @Autowired
    private TransferOrderService transferOrderService;

    @ApiOperation("分站条件分页查询")
    @PostMapping("{current}/{limit}")
    public Result pageList(
            @PathVariable Long current,
            @PathVariable Long limit,
            RegionStation regionStation
    ) {
        // 1 创建page对象，传递当前页和每页记录数
        // current：当前页
        // limit: 每页显示记录数
        Page<RegionStation> pageParam = new Page<>(current, limit);

        //2 调用service方法实现条件分页查询，返回分页对象
        IPage<RegionStation> pageModel = regionStationService.selectPage(pageParam, regionStation);

        return Result.ok(pageModel);
    }

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
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        RegionStation region = regionStationService.getById(id);
        return Result.ok(region);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody RegionStation region) {
        regionStationService.save(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody RegionStation region) {
        regionStationService.updateById(region);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        regionStationService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        regionStationService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("调拨入库")
    @GetMapping("/in/{orderId}")
    public Result out(@PathVariable Long orderId) {
        // 修改任务单状态
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.IN);
        workOrderService.updateByOrderId(workOrder);

        // 修改调拨单
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(orderId);
        transferOrder.setInTime(new Date());
        transferOrderService.updateByOrderId(transferOrder);

        // 生成库存单，应该每个item一个单
        StorageOrder storageOrder = new StorageOrder();
        storageOrder.setOrderId(orderId);
        storageOrder.setStorageType(StorageType.IN);
        storageOrderService.save(storageOrder);

        // 修改验货单
        CheckOrder checkOrder = new CheckOrder();
        checkOrder.setInTime(new Date());
        checkOrderService.updateByOrderId(checkOrder);

        return Result.ok(null);
    }

}

