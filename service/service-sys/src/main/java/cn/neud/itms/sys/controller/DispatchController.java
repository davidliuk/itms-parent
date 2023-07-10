package cn.neud.itms.sys.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.TransferStatus;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.model.sys.TransferOrder;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "调度管理")
@RestController
@RequestMapping("/admin/sys/dispatch")
//@CrossOrigin
public class DispatchController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private LogisticsService logisticsService;

    @Autowired
    private TransferOrderService transferOrderService;

    @Autowired
    private CheckOrderService checkOrderService;

    @Autowired
    private ReceiptService receiptService;

    @Autowired
    private InvoiceService invoiceService;

    @ApiOperation("手动调度")
    @GetMapping("manual")
    @SaCheckRole(value = {RoleConstant.DISPATCH, RoleConstant.SYSTEM}, mode = SaMode.OR)
    public Result manualDispatch(
            @RequestParam("orderNo") String orderNo,
            @RequestParam("stationId") Long stationId,
            @RequestParam("stationName") String stationName,
            @RequestParam("logisticsId") Long logisticsId,
            @RequestParam("logisticsName") String logisticsName,
            @RequestParam("logisticsPhone") String logisticsPhone
    ) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        orderInfo.setStationId(stationId);
        orderInfo.setStationName(stationName);
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);
        orderInfo.setLogisticsId(logisticsId);
        orderInfo.setLogisticsName(logisticsName);
        orderInfo.setLogisticsPhone(logisticsPhone);
        orderFeignClient.updateOrderInfo(orderInfo);

        // 修改任务单
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        workOrder.setStationId(stationId);
        workOrder.setStationName(stationName);
        workOrder.setLogisticsId(logisticsId);
        workOrder.setLogisticsName(logisticsName);
        workOrder.setLogisticsPhone(logisticsPhone);
        workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);

        // 修改调拨单
        TransferOrder transferOrder = new TransferOrder();
        BeanUtils.copyProperties(workOrder, transferOrder);
        transferOrderService.updateByOrderId(transferOrder, WorkType.DELIVERY);

        return Result.ok(null);
    }

    @ApiOperation("自动调度")
    @GetMapping("inner/{orderNo}}")
    public Result autoDispatch(
            @PathVariable String orderNo
    ) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);
        if (orderInfo.getOrderStatus() != OrderStatus.PAID) {
            return Result.fail("订单状态不是待调度状态");
        }

        // 分配运输公司
        Logistics logistics = logisticsService.getOne(new LambdaQueryWrapper<Logistics>()
                .eq(Logistics::getWareId, orderInfo.getWareId())
        );
        if (logistics == null) {
            return Result.fail("没有找到运输公司");
        }
        orderInfo.setLogisticsId(logistics.getId());
        orderInfo.setLogisticsName(logistics.getName());
        orderInfo.setLogisticsPhone(logistics.getPhone());
        orderFeignClient.updateOrderInfo(orderInfo);

        // 生成任务单
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        BeanUtils.copyProperties(orderInfo, workOrder);
        workOrder.setStationId(orderInfo.getStationId());
        workOrder.setLogisticsId(logistics.getId());
        workOrder.setLogisticsName(logistics.getName());
        workOrder.setLogisticsPhone(logistics.getPhone());
        workOrderService.save(workOrder);

        // 生成调拨单
        TransferOrder transferOrder = new TransferOrder();
        BeanUtils.copyProperties(workOrder, transferOrder);
        transferOrder.setWorkOrderId(workOrder.getId());
        transferOrder.setType(WorkType.DELIVERY);
        transferOrderService.save(transferOrder);
        return Result.ok(null);
    }

    @ApiOperation("获取订单详情")
    @GetMapping("orderDetail/{orderNo}")
    public Result getOrderDetail(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        Long orderId = orderInfo.getId();
        orderInfo.setWorkOrder(workOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setTransferOrder(transferOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setCheckOrder(checkOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setReceipt(receiptService.getByOrderId(orderId));
        orderInfo.setInvoice(invoiceService.getByOrderId(orderId));
        return Result.ok(orderInfo);
    }

    @ApiOperation("退货自动调度")
    @GetMapping("/inner/returnOrder/{orderNo}")
    void returnOrder(String orderNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        OrderStatus status = orderInfo.getOrderStatus();
        Long orderId = orderInfo.getId();
        WorkOrder workOrder = workOrderService.getByOrderId(orderId, WorkType.DELIVERY);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.CANCEL);
        workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);
        if (status == OrderStatus.DISPATCH) {
            // 没发货直接取消，无需生成退货任务单
            // 修改调拨单
            TransferOrder transferOrder = new TransferOrder();
            transferOrder.setOrderId(orderId);
            transferOrder.setStatus(TransferStatus.CANCEL);
            transferOrderService.updateByOrderId(transferOrder, WorkType.DELIVERY);
        } else if (status == OrderStatus.OUT) {
            // 这种情况需要分站点到货，然后点退货
            // 生成退货任务单
            WorkOrder returnWorkOrder = new WorkOrder();
            BeanUtils.copyProperties(orderInfo, returnWorkOrder);
            returnWorkOrder.setWorkType(WorkType.RETURN);
            returnWorkOrder.setWorkStatus(WorkStatus.RETURN_ASSIGN);
            workOrderService.save(returnWorkOrder);
            // 生成调拨单
            TransferOrder transferOrder = new TransferOrder();
            BeanUtils.copyProperties(workOrder, transferOrder);
            transferOrder.setWorkOrderId(workOrder.getId());
            transferOrder.setType(WorkType.RETURN);
            transferOrderService.save(transferOrder);
        } else if (status == OrderStatus.IN || status == OrderStatus.ASSIGN) {
            // 这种情况需要分站点退货
            // 到分站或者已分配状态，生成新的单并且退回
            WorkOrder returnWorkOrder = new WorkOrder();
            BeanUtils.copyProperties(orderInfo, returnWorkOrder);
            returnWorkOrder.setWorkType(WorkType.RETURN);
            returnWorkOrder.setWorkStatus(WorkStatus.RETURN_STATION);
            workOrderService.save(returnWorkOrder);
            // 生成调拨单
            TransferOrder transferOrder = new TransferOrder();
            BeanUtils.copyProperties(workOrder, transferOrder);
            transferOrder.setWorkOrderId(workOrder.getId());
            transferOrder.setType(WorkType.RETURN);
            transferOrderService.save(transferOrder);
        } else {
            // 这种情况需要配送员去接货，配送员确认接货，配送员送回分站
            WorkOrder returnWorkOrder = new WorkOrder();
            BeanUtils.copyProperties(orderInfo, returnWorkOrder);
            returnWorkOrder.setWorkType(WorkType.RETURN);
            returnWorkOrder.setWorkStatus(WorkStatus.RETURN_UNASSIGNED);
            workOrderService.save(returnWorkOrder);
            // 生成调拨单
            TransferOrder transferOrder = new TransferOrder();
            BeanUtils.copyProperties(workOrder, transferOrder);
            transferOrder.setWorkOrderId(workOrder.getId());
            transferOrder.setType(WorkType.RETURN);
            transferOrderService.save(transferOrder);
        }

    }

}
