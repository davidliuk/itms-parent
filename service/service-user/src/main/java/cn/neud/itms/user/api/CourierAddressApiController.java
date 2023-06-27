package cn.neud.itms.user.api;

import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/courier")
public class CourierAddressApiController {

    @Autowired
    private UserService userService;

    //根据userId查询提货点和配送员信息
    @GetMapping("/inner/getUserAddressByUserId/{userId}")
    public CourierAddressVo getUserAddressByUserId(@PathVariable("userId") Long userId) {
        return userService.getCourierAddressByUserId(userId);
    }

    //根据userId查询提货点和配送员信息
    @GetMapping("/inner/getAddressByUserId/{userId}")
    public AddressVo getAddressByUserId(@PathVariable("userId") Long userId) {
        return userService.getAddressByUserId(userId);
    }
}
