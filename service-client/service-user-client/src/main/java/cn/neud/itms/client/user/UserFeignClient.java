package cn.neud.itms.client.user;

import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient("service-user")
public interface UserFeignClient {

    //根据userId查询提货点和配送员信息
    @GetMapping("/api/user/courier/inner/getUserAddressByUserId/{userId}")
    public CourierAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId);

    //根据userId查询收货地址信息
    @GetMapping("/api/user/courier/inner/getAddressByUserId/{userId}")
    public AddressVo getAddressByUserId(@PathVariable("userId") Long userId);

}
