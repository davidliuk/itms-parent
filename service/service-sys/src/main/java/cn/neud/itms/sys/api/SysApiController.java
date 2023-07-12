package cn.neud.itms.sys.api;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.PurchaseOrderService;
import cn.neud.itms.sys.service.StorageOrderService;
import cn.neud.itms.sys.service.WorkOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sys")
public class SysApiController {

    @Autowired
    private PurchaseOrderService purchaseOrderService;

    @Autowired
    private StorageOrderService storageOrderService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private WorkOrderService workOrderService;

    @PostMapping("inner/purchaseOrder")
    public Result savePurchaseOrder(@RequestBody PurchaseOrder purchaseOrder) {
        purchaseOrderService.save(purchaseOrder);

        return Result.ok(null);
    }

    @PostMapping("inner/storageOrder")
    public Result saveStorageOrder(@RequestBody StorageOrder storageOrder) {
        storageOrderService.save(storageOrder);
        return Result.ok(null);
    }

    @PostMapping("take/{orderId}")
    public Result takeWorkOrder(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfoById(orderId);
        if (orderInfo.getOrderStatus() != OrderStatus.ASSIGN) {
            return Result.fail("订单还未分配，不能接单");
        }
        orderInfo.setOrderStatus(OrderStatus.TAKE);
        orderFeignClient.updateOrderInfo(orderInfo);
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.TAKE);
        workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);
        return Result.ok(null);
    }

    @PostMapping("returnTake/{orderId}")
    public Result returnTakeWorkOrder(@PathVariable Long orderId) {
        WorkOrder workOrder = workOrderService.getByOrderId(orderId, WorkType.EXCHANGE);
        if (workOrder == null) {
            return Result.fail("该订单无退货任务单，不能接单");
        }
        if (workOrder.getWorkStatus() != WorkStatus.RETURN_ASSIGN) {
            return Result.fail("退货任务单还未分配，不能接单");
        }
        workOrder.setWorkStatus(WorkStatus.RETURN_TAKE);
        workOrderService.updateById(workOrder);
        return Result.ok(null);
    }

    @PostMapping("receive/{orderId}")
    public Result receiveOrder(@PathVariable Long orderId) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfoById(orderId);
        if (orderInfo.getOrderStatus() != OrderStatus.TAKE) {
            return Result.fail("订单还未配送，不能确认收获");
        }
        orderInfo.setOrderStatus(OrderStatus.RECEIVE);
        orderFeignClient.updateOrderInfo(orderInfo);
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderId);
        workOrder.setWorkStatus(WorkStatus.RECEIVE);
        workOrderService.updateByOrderId(workOrder, WorkType.DELIVERY);
        return Result.ok(null);
    }

}
