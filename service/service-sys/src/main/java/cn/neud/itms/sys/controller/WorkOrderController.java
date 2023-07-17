package cn.neud.itms.sys.controller;


import cn.neud.itms.client.user.UserFeignClient;
import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.WorkOrderService;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "任务单管理")
@RestController
@RequestMapping("/admin/sys/workOrder")
//@CrossOrigin
public class WorkOrderController {

    @Autowired
    private WorkOrderService workOrderService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    @Autowired
    private UserFeignClient userFeignClient;

    // 开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("任务单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody WorkOrderQueryVo workOrderQueryVo
    ) {
        Page<WorkOrder> pageParam = new Page<>(page, limit);
        IPage<WorkOrder> pageModel = workOrderService.selectPage(pageParam, workOrderQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("修改任务单")
    @PutMapping
    public Result update(@RequestBody WorkOrder workOrder) {
        workOrderService.updateById(workOrder);
        return Result.ok(null);
    }

    @ApiOperation("删除任务单")
    @DeleteMapping
    public Result delete(@RequestBody WorkOrder workOrder) {
        workOrderService.updateById(workOrder);
        return Result.ok(null);
    }

    // 分配任务单
    @ApiOperation("分配任务单")
    @GetMapping("/assign/{workOrderId}/{courierId}")
    public Result assign(
            @PathVariable Long workOrderId,
            @PathVariable Long courierId
    ) {
        WorkOrder workOrder = workOrderService.getById(workOrderId);
        if (workOrder == null) {
            throw new ItmsException(ResultCodeEnum.ORDER_NOT_EXIST);
        }
        // 未分配或已分配
        if (workOrder.getWorkStatus() != WorkStatus.IN &&
                workOrder.getWorkStatus() != WorkStatus.ASSIGN) {
            throw new ItmsException(ResultCodeEnum.ORDER_STATUS_ERROR);
        }
        // 修改订单状态
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(workOrder.getOrderId());
        orderInfo.setOrderStatus(OrderStatus.ASSIGN);
        orderInfo.setAssignTime(new Date());
        orderFeignClient.updateOrderInfo(orderInfo);
        // 修改任务单状态
        workOrder.setCourierId(courierId);
        workOrder.setWorkStatus(WorkStatus.ASSIGN);
        workOrderService.updateById(workOrder);
        userFeignClient.addWorkNum(courierId, 1);
        return Result.ok(null);
    }

    // 分配任务单
    @ApiOperation("退货分配任务单")
    @GetMapping("/returnOrder/assign/{workOrderId}/{courierId}")
    public Result returnOrderAssign(
            @PathVariable Long workOrderId,
            @PathVariable Long courierId
    ) {
        WorkOrder workOrder = workOrderService.getById(workOrderId);
        if (workOrder == null) {
            throw new ItmsException(ResultCodeEnum.WORK_ORDER_STATUS_ERROR);
        }
        // 未分配或已分配
        if (workOrder.getWorkStatus() != WorkStatus.RETURN_ASSIGN &&
                workOrder.getWorkStatus() != WorkStatus.RETURN_UNASSIGNED) {
            throw new ItmsException(ResultCodeEnum.WORK_ORDER_STATUS_ERROR);
        }
        // 修改任务单状态
        workOrder.setCourierId(courierId);
        workOrder.setWorkStatus(WorkStatus.RETURN_ASSIGN);
        workOrderService.updateById(workOrder);
        userFeignClient.addWorkNum(courierId, 1);
        return Result.ok(null);
    }

}

