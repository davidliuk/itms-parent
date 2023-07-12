package cn.neud.itms.order.client;

import cn.neud.itms.model.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface OrderFeignClient {

    @GetMapping("/api/order/inner/getOrderDetailByNo/{orderNo}")
    public OrderInfo getOrderDetailByNo(@PathVariable("orderNo") String orderNo);

    @GetMapping("/api/order/inner/getOrderInfoByNo/{orderNo}")
    public OrderInfo getOrderInfoByNo(@PathVariable("orderNo") String orderNo);
//    @GetMapping("/api/order/auth/getOrderInfoById/{orderId}")
//    public OrderInfo getOrderInfoById(@PathVariable("orderId") Long orderId);

    @GetMapping("/api/order/inner/getOrderInfoById/{orderId}")
    public OrderInfo getOrderInfoById(@PathVariable("orderId") Long orderId);

    @GetMapping("/api/order/inner/getOrderDetailById/{orderId}")
    public OrderInfo getOrderDetailById(@PathVariable("orderId") Long orderId);

    @PutMapping("/api/order/inner/updateOrderInfo")
    public void updateOrderInfo(@RequestBody OrderInfo orderInfo);
}
