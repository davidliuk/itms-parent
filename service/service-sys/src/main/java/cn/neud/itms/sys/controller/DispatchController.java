package cn.neud.itms.sys.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.enums.*;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.Invoice;
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

import java.util.List;

/**
 * <p>
 * 地区表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
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

    @ApiOperation("获取订单详情")
    @GetMapping("orderDetail/{orderNo}")
    public Result getOrderDetail(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderDetailByNo(orderNo);
        if (orderInfo == null) {
            throw new ItmsException(ResultCodeEnum.ORDER_NOT_EXIST);
        }
        Long orderId = orderInfo.getId();
        orderInfo.setWorkOrder(workOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setTransferOrder(transferOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setCheckOrder(checkOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setReceipt(receiptService.getByOrderId(orderId));
        orderInfo.setInvoice(invoiceService.getByOrderId(orderId));
        return Result.ok(orderInfo);
    }

    @ApiOperation("获取订单详情根据ID")
    @GetMapping("orderDetailById/{orderId}")
    public Result getOrderDetailById(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderFeignClient.getOrderDetailById(orderId);
        if (orderInfo == null) {
            throw new ItmsException(ResultCodeEnum.ORDER_NOT_EXIST);
        }
        orderInfo.setWorkOrder(workOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setTransferOrder(transferOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setCheckOrder(checkOrderService.getByOrderId(orderId, WorkType.DELIVERY));
        orderInfo.setReceipt(receiptService.getByOrderId(orderId));
        orderInfo.setInvoice(invoiceService.getByOrderId(orderId));
        return Result.ok(orderInfo);
    }

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
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByNo(orderNo);
        if (orderInfo == null) {
            throw new ItmsException(ResultCodeEnum.ORDER_NOT_EXIST);
        }
        if (orderInfo.getOrderStatus() != OrderStatus.PAID ||
                orderInfo.getWorkOrder().getWorkStatus() != WorkStatus.DISPATCH
        ) {
            throw new ItmsException(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
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
    public Result autoDispatch(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByNo(orderNo);
        if (orderInfo == null) {
            throw new ItmsException(ResultCodeEnum.ORDER_NOT_EXIST);
        }
        if (orderInfo.getOrderStatus() != OrderStatus.PAID) {
            throw new ItmsException(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);
        List<Logistics> list = logisticsService.list(new LambdaQueryWrapper<Logistics>()
                .eq(Logistics::getWareId, orderInfo.getWareId()));
        if (list == null || list.size() == 0) {
            return Result.fail("没有找到运输公司");
        }
        Logistics logistics = list.get(0);
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
        // 生成发票
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderInfo.getId());
        BeanUtils.copyProperties(orderInfo, invoice);
        invoice.setStatus(InvoiceStatus.UNUSED);
        invoiceService.save(invoice);
        return Result.ok(null);
    }

    @ApiOperation("退货自动调度")
    @GetMapping("/inner/returnOrder/{orderNo}")
    void returnOrder(@PathVariable String orderNo) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfoByNo(orderNo);
        OrderStatus status = orderInfo.getOrderStatus();
        Long orderId = orderInfo.getId();
        // 修改订单状态
        WorkOrder workOrder = workOrderService.getByOrderId(orderId, WorkType.DELIVERY);
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.CANCEL);
        workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);
        // 修改发票状态：作废
        Invoice invoice = new Invoice();
        invoice.setOrderId(orderInfo.getId());
        invoice.setStatus(InvoiceStatus.CANCEL);
        invoiceService.updateByOrderId(invoice);

        if (status == OrderStatus.DISPATCH) {
            // 没发货直接取消，无需生成退货任务单
            // 修改调拨单状态
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
            returnWorkOrder.setWorkStatus(WorkStatus.RETURN_TAKE);
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
            // 生成退货任务单
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
        } else if (status == OrderStatus.TAKE) {
            // 生成退货任务单，原配送员
            WorkOrder returnWorkOrder = new WorkOrder();
            BeanUtils.copyProperties(orderInfo, returnWorkOrder);
            returnWorkOrder.setWorkType(WorkType.RETURN);
            returnWorkOrder.setWorkStatus(WorkStatus.RETURN_TAKE);
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
