package cn.neud.itms.user.service.impl;

import cn.neud.itms.model.user.Courier;
import cn.neud.itms.model.user.User;
import cn.neud.itms.model.user.UserDelivery;
import cn.neud.itms.user.mapper.CourierMapper;
import cn.neud.itms.user.mapper.UserDeliveryMapper;
import cn.neud.itms.user.mapper.UserMapper;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.CourierAddressVo;
import cn.neud.itms.vo.user.UserLoginVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private CourierMapper courierMapper;

    // 判断是否是第一次使用微信授权登录：如何判断？openId
    @Override
    public User getUserByOpenId(String openid) {
        User user = baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenId, openid)
        );
        return user;
    }

    //5 根据userId查询提货点和配送员信息
    @Override
    public CourierAddressVo getCourierAddressByUserId(Long userId) {
        //根据userId查询用户默认的配送员id
        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>()
                        .eq(UserDelivery::getUserId, userId)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery == null) {
            return null;
        }
        //拿着上面查询配送员id查询courier表查询配送员其他信息
        Courier courier = courierMapper.selectById(userDelivery.getCourierId());
        //封装数据到CourierAddressVo
        CourierAddressVo courierAddressVo = new CourierAddressVo();
        BeanUtils.copyProperties(courier, courierAddressVo);
        courierAddressVo.setUserId(userId);
        courierAddressVo.setCourierId(courier.getId());
        courierAddressVo.setCourierName(courier.getName());
        courierAddressVo.setCourierPhone(courier.getPhone());
        courierAddressVo.setWareId(userDelivery.getWareId());
        courierAddressVo.setStorePath(courier.getStorePath());
        return courierAddressVo;
    }

    //7 获取当前登录用户信息，
    @Override
    public UserLoginVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserLoginVo userLoginVo = new UserLoginVo();
        userLoginVo.setUserId(id);
        userLoginVo.setNickName(user.getNickName());
        userLoginVo.setPhotoUrl(user.getPhotoUrl());
        userLoginVo.setIsNew(user.getIsNew());
        userLoginVo.setOpenId(user.getOpenId());

        UserDelivery userDelivery = userDeliveryMapper.selectOne(
                new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id)
                        .eq(UserDelivery::getIsDefault, 1)
        );
        if (userDelivery != null) {
            userLoginVo.setCourierId(userDelivery.getCourierId());
            userLoginVo.setWareId(userDelivery.getWareId());
        } else {
            userLoginVo.setCourierId(1L);
            userLoginVo.setWareId(1L);
        }
        return userLoginVo;
    }
}
