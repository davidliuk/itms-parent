package cn.neud.itms.payment.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.payment.service.PaymentInfoService;
import cn.neud.itms.payment.service.WeixinService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@Api(tags = "支付接口")
@RestController
@RequestMapping("/api/payment")
public class PaymentApiController {

    @Autowired
    private PaymentInfoService paymentInfoService;

    @ApiOperation(value = "支付订单")
    @GetMapping("/{orderNo}")
    public Result queryPayStatus(@PathVariable("orderNo") String orderNo) {
        //1 调用微信支付系统接口查询订单支付状态
        //3 如果微信支付系统返回值，判断支付成功
        paymentInfoService.paySuccess(orderNo);
        return Result.ok(null);
    }

}
