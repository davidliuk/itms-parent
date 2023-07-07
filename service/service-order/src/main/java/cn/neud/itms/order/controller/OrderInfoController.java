package cn.neud.itms.order.controller;


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
 * @author neud
 * @since 2023-04-18
 */
@Api(tags = "订单管理")
@RestController
@RequestMapping(value = "/api/order")
public class OrderInfoController {

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
            @PathVariable("orderNo") String orderNo) {
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

    @SaUserCheckLogin
    @ApiOperation("生成订单")
    @PostMapping("auth/submitOrder")
    public Result submitOrder(@RequestBody OrderSubmitVo orderParamVo) {
        Long orderId = orderInfoService.submitOrder(orderParamVo);
        return Result.ok(orderId);
    }

    @SaUserCheckLogin
    @ApiOperation("获取订单详情")
    @GetMapping("auth/getOrderInfoById/{orderId}")
    public Result getOrderInfoById(@PathVariable("orderId") Long orderId) {
        OrderInfo orderInfo = orderInfoService.getOrderInfoById(orderId);
        return Result.ok(orderInfo);
    }

    @ApiOperation("修改订单地址")
    @SaUserCheckLogin
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

    // 根据orderNo查询订单信息
    @ApiOperation("根据orderNo查询订单信息")
    @GetMapping("inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable("orderNo") String orderNo) {
        return orderInfoService.getOrderInfoByOrderNo(orderNo);
    }

    // 根据orderNo查询订单信息
    @PutMapping("inner/updateOrderInfo")
    public boolean updateOrderInfo(@RequestBody OrderInfo orderInfo) {
        return orderInfoService.updateById(orderInfo);
    }
}

