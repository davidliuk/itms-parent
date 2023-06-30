package cn.neud.itms.sys.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.WorkOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@Api(tags = "调度控制器")
@RestController
@RequestMapping("/admin/sys/dispatch")
//@CrossOrigin
public class DispatchController {

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private WorkOrderService workOrderService;

    @ApiOperation("手动调度")
    @GetMapping("manual")
    @SaCheckRole(value = {RoleConstant.DISPATCH, RoleConstant.SYSTEM}, mode = SaMode.OR)
    public Result manualDispatch(
            @RequestParam("orderNo") String orderNo,
            @RequestParam("stationId") Long stationId,
            @RequestParam("stationName") String stationName
    ) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        orderInfo.setStationId(stationId);
        orderInfo.setStationName(stationName);
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);
        orderFeignClient.updateOrderInfo(orderInfo);
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        workOrder.setStationId(stationId);
        workOrderService.save(workOrder);
        workOrderService.updateByOrderId(workOrder);
        return Result.ok(null);
    }

    @ApiOperation("自动调度")
    @GetMapping("inner/{orderNo}}")
    public Result autoDispatch(
            @PathVariable String orderNo
    ) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);
        orderFeignClient.updateOrderInfo(orderInfo);
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        workOrder.setStationId(orderInfo.getStationId());
        workOrderService.save(workOrder);
        return Result.ok(null);
    }

}
