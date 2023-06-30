package cn.neud.itms.sys.client;

import cn.neud.itms.model.order.OrderInfo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("service-sys")
public interface SysFeignClient {

    @GetMapping("/api/sys/dispatch/inner/{orderNo}")
    public void autoDispatch(@PathVariable String orderNo);

}
