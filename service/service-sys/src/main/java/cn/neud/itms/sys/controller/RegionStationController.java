package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.*;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.*;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
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

    @Autowired
    private OrderFeignClient orderFeignClient;

    @ApiOperation(value = "根据仓库ID获取")
    @GetMapping("getByWareId/{wareId}")
    public Result getByWareId(@PathVariable Long wareId) {
        List<RegionStation> stations = regionStationService.list(new LambdaQueryWrapper<RegionStation>()
                .eq(RegionStation::getWareId, wareId));
        return Result.ok(stations);
    }

    @ApiOperation("分站条件分页查询")
    @PostMapping("{current}/{limit}")
    public Result pageList(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody RegionStation regionStation
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

    @ApiOperation("根据地区id查询")
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
        // 修改订单状态
        OrderInfo orderInfo = orderFeignClient.getOrderInfoById(orderId);
        if (orderInfo == null) {
            return Result.fail("订单不存在");
        }
        if (orderInfo.getOrderStatus() != OrderStatus.OUT && orderInfo.getOrderStatus() != OrderStatus.CANCEL) {
            return Result.fail("订单状态不正确");
        }
        if (orderInfo.getOrderStatus() == OrderStatus.OUT) {
            orderInfo.setId(orderId);
            orderInfo.setOrderStatus(OrderStatus.IN);
            orderInfo.setInTime(new Date());
            orderFeignClient.updateOrderInfo(orderInfo);
            // 修改任务单
            WorkOrder workOrder = new WorkOrder();
            workOrder.setOrderId(orderId);
            workOrder.setWorkStatus(WorkStatus.IN);
            workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);
        } else {
            // 修改任务单
            WorkOrder workOrder = new WorkOrder();
            workOrder.setOrderId(orderId);
            workOrder.setWorkStatus(WorkStatus.RETURN_STATION);
            workOrderService.updateByOrderId(workOrder, WorkType.RETURN);
        }

        // 修改调拨单
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(orderId);
        transferOrder.setStatus(TransferStatus.IN);
        transferOrder.setInTime(new Date());
        transferOrderService.updateByOrderId(transferOrder, WorkType.DELIVERY);

        // 生成库存单，应该每个item一个单
        StorageOrder storageOrder = new StorageOrder();
        storageOrder.setOrderId(orderId);
        storageOrder.setStorageType(StorageType.IN);
        storageOrderService.save(storageOrder);

        // 修改验货单
        CheckOrder checkOrder = new CheckOrder();
        checkOrder.setOrderId(orderId);
        checkOrder.setInTime(new Date());
        checkOrder.setStatus(CheckStatus.IN);
        checkOrderService.updateByOrderId(checkOrder, WorkType.DELIVERY);

        return Result.ok(null);
    }

    @ApiOperation("退货入站")
    @GetMapping("/returnOrder/in/{orderId}")
    public Result in(@PathVariable Long orderId) {
        // 获取任务单
        WorkOrder workOrder = workOrderService.getByOrderId(orderId, WorkType.RETURN);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.RETURN_STATION);
        workOrderService.updateByOrderId(workOrder, WorkType.RETURN);

//        // 修改调拨单
//        TransferOrder transferOrder = new TransferOrder();
//        transferOrder.setOrderId(orderId);
//        transferOrder.setOutTime(new Date());
//        transferOrderService.save(transferOrder);
//
//        // 生成库存单，应该每个item一个单
//        StorageOrder storageOrder = new StorageOrder();
//        BeanUtils.copyProperties(workOrder, storageOrder);
//        storageOrder.setStorageType(StorageType.OUT);
//        storageOrderService.save(storageOrder);
//
//        // 生成验货单
//        CheckOrder checkOrder = new CheckOrder();
//        BeanUtils.copyProperties(workOrder, checkOrder);
//        checkOrder.setOutTime(new Date());
//        checkOrder.setType(WorkType.RETURN);
//        checkOrder.setStatus(CheckStatus.OUT);
//        checkOrderService.save(checkOrder);

        return Result.ok(null);
    }

    @ApiOperation("退货出库")
    @GetMapping("/returnOrder/out/{orderId}")
    public Result returnOrderOut(@PathVariable Long orderId) {
        // 修改任务单
        WorkOrder workOrder = workOrderService.getByOrderId(orderId, WorkType.RETURN);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.RETURN_OUT);
        workOrderService.updateByOrderId(workOrder, WorkType.RETURN);

        // 修改调拨单
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(orderId);
        transferOrder.setOutTime(new Date());
        transferOrder.setStatus(TransferStatus.OUT);
        transferOrderService.updateByOrderId(transferOrder, WorkType.RETURN);

        // 生成库存单，应该每个item一个单
        StorageOrder storageOrder = new StorageOrder();
        BeanUtils.copyProperties(workOrder, storageOrder);
        storageOrder.setStorageType(StorageType.RETURN_OUT);
        storageOrderService.save(storageOrder);

        // 生成验货单
        CheckOrder checkOrder = new CheckOrder();
        BeanUtils.copyProperties(workOrder, checkOrder);
        checkOrder.setOutTime(new Date());
        checkOrder.setType(WorkType.RETURN);
        checkOrder.setStatus(CheckStatus.OUT);
        checkOrderService.save(checkOrder);

        return Result.ok(null);
    }

}

