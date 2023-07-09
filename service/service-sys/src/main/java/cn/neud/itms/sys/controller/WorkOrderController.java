package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
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

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
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
            return Result.fail("任务单不存在");
        }
        if (workOrder.getWorkStatus() != WorkStatus.IN && workOrder.getWorkStatus() != WorkStatus.ASSIGN) {
            return Result.fail("任务单状态不正确");
        }
        OrderInfo orderInfo = new OrderInfo();
        orderInfo.setId(workOrder.getId());
        orderInfo.setOrderStatus(OrderStatus.ASSIGN);
        orderFeignClient.updateOrderInfo(orderInfo);
        workOrder.setCourierId(courierId);
        workOrderService.updateById(workOrder);
        return Result.ok(null);
    }

}

