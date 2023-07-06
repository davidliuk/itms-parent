package cn.neud.itms.sys.controller;


import cn.dev33.satoken.annotation.SaCheckRole;
import cn.dev33.satoken.annotation.SaMode;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.model.sys.TransferOrder;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.LogisticsService;
import cn.neud.itms.sys.service.TransferOrderService;
import cn.neud.itms.sys.service.WorkOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

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

        // 生成任务单
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        workOrder.setStationId(stationId);
        workOrder.setLogisticsId(logisticsId);
        workOrder.setLogisticsName(logisticsName);
        workOrder.setLogisticsPhone(logisticsPhone);
//        workOrderService.save(workOrder);
        workOrderService.updateByOrderId(workOrder);

        // 生成调拨单
        return Result.ok(null);
    }

    @ApiOperation("自动调度")
    @GetMapping("inner/{orderNo}}")
    public Result autoDispatch(
            @PathVariable String orderNo
    ) {
        OrderInfo orderInfo = orderFeignClient.getOrderInfo(orderNo);
        orderInfo.setStationId(orderInfo.getStationId());
        orderInfo.setOrderStatus(OrderStatus.DISPATCH);

        Logistics logistics = logisticsService.getOne(new LambdaQueryWrapper<Logistics>()
                .eq(Logistics::getWareId, orderInfo.getWareId())
        );
        orderInfo.setLogisticsId(logistics.getId());
        orderInfo.setLogisticsName(logistics.getName());
        orderInfo.setLogisticsPhone(logistics.getPhone());
        orderFeignClient.updateOrderInfo(orderInfo);

        // 生成任务单
        WorkOrder workOrder = new WorkOrder();
        workOrder.setOrderId(orderInfo.getId());
        workOrder.setStationId(orderInfo.getStationId());
        workOrder.setLogisticsId(logistics.getId());
        workOrder.setLogisticsName(logistics.getName());
        workOrder.setLogisticsPhone(logistics.getPhone());
        workOrderService.save(workOrder);

        // 生成调拨单
        TransferOrder transferOrder = new TransferOrder();
        BeanUtils.copyProperties(workOrder, transferOrder);
        transferOrder.setOutTime(new Date());
        transferOrder.setWorkOrderId(workOrder.getId());
        transferOrderService.save(transferOrder);

        return Result.ok(null);
    }

}
