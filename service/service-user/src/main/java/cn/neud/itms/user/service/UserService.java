package cn.neud.itms.user.service;

import cn.neud.itms.model.user.User;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import cn.neud.itms.vo.user.UserQueryVo;
import cn.neud.itms.vo.user.UserVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface UserService extends IService<User> {
    // 判断是否是第一次使用微信授权登录：如何判断？openId
    User getUserByOpenId(String openid);

    //5 根据userId查询提货点和配送员信息
    CourierAddressVo getCourierAddressByUserId(Long userId);

    public AddressVo getAddressByUserId(Long userId);

    //7 获取当前登录用户信息，
    UserVo getUserLoginVo(Long id);

    User getUserByUserName(String username);

    User getUserByEmail(String email);

    IPage<User> selectPageUser(Page<User> pageParam, UserQueryVo userQueryVo);
}
