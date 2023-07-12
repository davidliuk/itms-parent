package cn.neud.itms.order.api;


import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.user.Address;
import cn.neud.itms.order.service.OrderInfoService;
import cn.neud.itms.vo.order.OrderConfirmVo;
import cn.neud.itms.vo.order.OrderSubmitVo;
import cn.neud.itms.vo.order.OrderUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-04-18
 */
@Api(tags = "订单接口")
@RestController
@RequestMapping(value = "/api/order")
public class OrderInfoApiController {

    @Autowired
    private OrderInfoService orderInfoService;

    //订单查询
    @GetMapping("auth/findUserOrderPage/{page}/{limit}")
    @SaUserCheckLogin
    public Result findUserOrderPage(
            @ApiParam(name = "page", value = "当前页码", required = true)
            @PathVariable Long page,
            @ApiParam(name = "limit", value = "每页记录数", required = true)
            @PathVariable Long limit,
            @ApiParam(name = "orderVo", value = "查询对象", required = false)
            OrderUserQueryVo orderUserQueryVo
    ) {
        // 获取userId
        // Long userId = AuthContextHolder.getUserId();
        Long userId = StpUserUtil.getLoginIdAsLong();
        orderUserQueryVo.setUserId(userId);

        // 分页查询条件
        Page<OrderInfo> pageParam = new Page<>(page, limit);
        IPage<OrderInfo> pageModel = orderInfoService.getOrderInfoByUserIdPage(pageParam, orderUserQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "查询支付状态")
    @GetMapping("/queryPayStatus/{orderNo}")
    public Result queryPayStatus(
            @ApiParam(name = "orderNo", value = "订单No", required = true)
            @PathVariable("orderNo") String orderNo
    ) {
        System.out.println(new Date().toLocaleString());
        for (int i = 0; i <= 3; i++) {
            if (i == 3) {
                return Result.ok(ResultCodeEnum.SUCCESS);
            }
        }
        return Result.ok(ResultCodeEnum.URL_ENCODE_ERROR);
    }

    @ApiOperation("确认订单")
    @GetMapping("auth/confirmOrder")
    @SaUserCheckLogin
    public Result confirm() {
        OrderConfirmVo orderConfirmVo = orderInfoService.confirmOrder();
        return Result.ok(orderConfirmVo);
    }

    @ApiOperation("退货订单")
    @GetMapping("auth/returnOrder/{orderNo}")
    @SaUserCheckLogin
    public Result returnOrder(
            @ApiParam(name = "orderNo", value = "订单No", required = true)
            @PathVariable("orderNo") String orderNo
    ) {
        orderInfoService.returnOrder(orderNo);
        return Result.ok(null);
    }

    @SaUserCheckLogin
    @ApiOperation("生成订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo) {
        Long orderId = orderInfoService.submitOrder(orderParamVo);
        return Result.ok(orderId);
    }

    @ApiOperation("修改订单地址")
//    @SaUserCheckLogin
    // 根据orderNo查询订单信息
    @PutMapping("auth/changeOrderAddress/{orderNo}")
    public Result changeOrderAddress(@PathVariable("orderNo") String orderNo, @RequestBody Address address) {
        OrderInfo orderInfo = orderInfoService.getById(orderNo);
        if (orderInfo == null) {
            return Result.fail("订单不存在");
        }
        if (orderInfo.getUserId() != StpUserUtil.getLoginIdAsLong()) {
            return Result.fail("订单不属于当前用户");
        }
        BeanUtils.copyProperties(address, orderInfo);
        orderInfoService.updateById(orderInfo);
        return Result.ok(null);
    }

    @SaUserCheckLogin
    @ApiOperation("获取订单详情")
    @GetMapping("auth/getOrderDetailById/{orderId}")
    public Result getOrderInfoById(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.ok(orderInfo);
    }

    // 根据orderNo查询订单信息
    @ApiOperation("根据orderNo查询订单基础信息")
    @GetMapping("inner/getOrderInfoByNo/{orderNo}")
    public OrderInfo getOrderInfoByNo(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.getOrderInfoByOrderNo(orderNo);
    }

    // 根据orderNo查询订单信息
    @ApiOperation("根据orderNo查询订单详情")
    @GetMapping("inner/getOrderDetailByNo/{orderNo}")
    public OrderInfo getOrderDetailByNo(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.getOrderDetailByOrderNo(orderNo);
    }

    @ApiOperation("获取订单详情")
    @GetMapping("inner/getOrderDetaiById/{orderId}")
    public OrderInfo getOrderById(@PathVariable("orderId") Long orderId) {
        return orderInfoService.getOrderInfoById(orderId);
    }

//    @ApiOperation("根据orderId查询订单信息")
//    @GetMapping("inner/getOrderInfo/{orderId}")
//    public OrderInfo getOrderInfoById(@PathVariable("orderId") Long orderId) {
//        return orderInfoService.getOrderInfoById(orderId);
//    }

    @PutMapping("inner/updateOrderInfo")
    public void updateOrderInfo(@RequestBody OrderInfo orderInfo) {
        orderInfoService.updateById(orderInfo);
    }
}

