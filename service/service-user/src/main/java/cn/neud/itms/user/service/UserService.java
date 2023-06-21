package cn.neud.itms.user.service;

import cn.neud.itms.model.user.User;
import cn.neud.itms.vo.user.CourierAddressVo;
import cn.neud.itms.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    //// 判断是否是第一次使用微信授权登录：如何判断？openId
    User getUserByOpenId(String openid);

    //5 根据userId查询提货点和配送员信息
    CourierAddressVo getCourierAddressByUserId(Long userId);

    //7 获取当前登录用户信息，
    UserLoginVo getUserLoginVo(Long id);
}
