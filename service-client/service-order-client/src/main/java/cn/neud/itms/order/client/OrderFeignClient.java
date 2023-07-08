package cn.neud.itms.order.client;

import cn.neud.itms.model.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-order")
public interface OrderFeignClient {

    @GetMapping("/api/order/inner/getOrderInfo/{orderNo}")
    public OrderInfo getOrderInfo(@PathVariable("orderNo") String orderNo);

    @PutMapping("/api/order/inner/updateOrderInfo")
    public void updateOrderInfo(@RequestBody OrderInfo orderInfo);
}
