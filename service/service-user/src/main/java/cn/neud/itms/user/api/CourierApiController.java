package cn.neud.itms.user.api;

import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.auth.StpUserUtil;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.sys.client.SysFeignClient;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user/courier")
public class CourierApiController {

    @Autowired
    private UserService userService;

    @Autowired
    private SysFeignClient sysFeignClient;

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

    // 根据配送员id分页查询配送任务单
    @SaUserCheckLogin
    @GetMapping("/inner/getTaskByCourierId/{current}/{limit}")
    public Result getTaskByCourierId(
            @PathVariable("current") Long current,
            @PathVariable("limit") Long limit
    ) {
        WorkOrderQueryVo workOrderQueryVo = new WorkOrderQueryVo();
        workOrderQueryVo.setCourierId(StpUserUtil.getLoginIdAsLong());
        return sysFeignClient.list(current, limit, workOrderQueryVo);
    }
}