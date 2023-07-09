package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.StorageType;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.*;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.*;
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
 * 仓库表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "区域中心仓库管理接口")
@RestController
@RequestMapping("/admin/sys/ware")
//@CrossOrigin
public class WareController {

    @Autowired
    private WareService wareService;

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

    @ApiOperation("分站条件分页查询")
    @PostMapping("{current}/{limit}")
    public Result<IPage<Ware>> pageList(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody Ware ware
    ) {
        // 1 创建page对象，传递当前页和每页记录数
        // current：当前页
        // limit: 每页显示记录数
        Page<Ware> pageParam = new Page<>(current, limit);

        //2 调用service方法实现条件分页查询，返回分页对象
        IPage<Ware> pageModel = wareService.selectPage(pageParam, ware);

        return Result.ok(pageModel);
    }

    //查询所有仓库列表
//    url: `${api_name}/findAllList`,
//    method: 'get'
    @ApiOperation("查询所有仓库列表")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Ware> list = wareService.list();
        return Result.ok(list);
    }

    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Ware ware = wareService.getById(id);
        return Result.ok(ware);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody Ware ware) {
        wareService.save(ware);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody Ware ware) {
        wareService.updateById(ware);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        wareService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        wareService.removeByIds(idList);
        return Result.ok(null);
    }

    @ApiOperation("调拨出库")
    @GetMapping("/out/{orderId}")
    public Result out(@PathVariable Long orderId) {
        // 修改订单状态
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setOrderStatus(OrderStatus.OUT);
        orderInfo.setOutTime(new Date());
        orderFeignClient.updateOrderInfo(orderInfo);

        // 修改任务单
        WorkOrder workOrder = workOrderService.getByOrderId(orderId);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.OUT);
        workOrderService.updateByOrderId(workOrder);

        // 修改调拨单
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(orderId);
        transferOrder.setOutTime(new Date());
        transferOrderService.updateByOrderId(transferOrder);

        // 生成库存单，应该每个item一个单
        StorageOrder storageOrder = new StorageOrder();
        BeanUtils.copyProperties(workOrder, storageOrder);
        storageOrder.setStorageType(StorageType.OUT);
        storageOrderService.save(storageOrder);

        // 生成验货单
        CheckOrder checkOrder = new CheckOrder();
        BeanUtils.copyProperties(workOrder, checkOrder);
        checkOrder.setOutTime(new Date());
        checkOrderService.save(checkOrder);

        return Result.ok(null);
    }

    @ApiOperation("退货入库")
    @GetMapping("/in/{orderId}")
    public Result in(@PathVariable Long orderId) {
        // 修改订单状态
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(orderId);
        orderInfo.setOrderStatus(OrderStatus.OUT);
        orderInfo.setInTime(new Date());
        orderFeignClient.updateOrderInfo(orderInfo);

        // 获取任务单
        WorkOrder workOrder = workOrderService.getByOrderId(orderId);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.OUT);
        workOrderService.updateByOrderId(workOrder);

        // 生成调拨单
        TransferOrder transferOrder = new TransferOrder();
        transferOrder.setOrderId(orderId);
        transferOrder.setOutTime(new Date());
        transferOrderService.save(transferOrder);

        // 生成库存单，应该每个item一个单
        StorageOrder storageOrder = new StorageOrder();
        BeanUtils.copyProperties(workOrder, storageOrder);
        storageOrder.setStorageType(StorageType.OUT);
        storageOrderService.save(storageOrder);

        // 生成验货单
        CheckOrder checkOrder = new CheckOrder();
        BeanUtils.copyProperties(workOrder, checkOrder);
        checkOrder.setOutTime(new Date());
        checkOrderService.save(checkOrder);

        return Result.ok(null);
    }

}

