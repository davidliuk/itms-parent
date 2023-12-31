package cn.neud.itms.user.service.impl;

import cn.neud.itms.enums.UserType;
import cn.neud.itms.model.user.Address;
import cn.neud.itms.model.user.CourierInfo;
import cn.neud.itms.model.user.User;
import cn.neud.itms.model.user.UserDelivery;
import cn.neud.itms.user.mapper.AddressMapper;
import cn.neud.itms.user.mapper.CourierMapper;
import cn.neud.itms.user.mapper.UserDeliveryMapper;
import cn.neud.itms.user.mapper.UserMapper;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import cn.neud.itms.vo.user.UserQueryVo;
import cn.neud.itms.vo.user.UserVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private UserDeliveryMapper userDeliveryMapper;

    @Autowired
    private AddressMapper addressMapper;

    @Autowired
    private CourierMapper courierMapper;

    // 判断是否是第一次使用微信授权登录：如何判断？openId
    @Override
    public User getUserByOpenId(String openid) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getOpenId, openid)
        );
    }

    @Override
    public User getUserByUserName(String username) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getUsername, username)
        );
    }

    @Override
    public User getUserByEmail(String email) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<User>().eq(User::getEmail, email)
        );
    }

    @Override
    public IPage<User> selectPageUser(Page<User> pageParam, UserQueryVo userQueryVo) {
        //获取条件值
        UserType userType = userQueryVo.getUserType();
        String userName = userQueryVo.getUserName();
        String nickName = userQueryVo.getNickName();
        String idNo = userQueryVo.getIdNo();

        //创建mp条件对象
        LambdaQueryWrapper<User> wrapper = new LambdaQueryWrapper<User>()
                .eq(userType != null, User::getUserType, userType)
                .like(!StringUtils.isEmpty(userName), User::getUsername, userName)
                .like(!StringUtils.isEmpty(nickName), User::getNickName, nickName)
                .like(!StringUtils.isEmpty(idNo), User::getIdNo, idNo);
        // 调用方法实现条件分页查询
        // 返回分页对象
        return baseMapper.selectPage(pageParam, wrapper);
    }

    // 5 根据userId查询提货点和配送员信息
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
        //根据userId查询用户默认的配送员id
        Address address = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getUserId, userId)
                        .eq(Address::getIsDefault, 1)
        );
        if (address == null) {
            return null;
        }
        //拿着上面查询配送员id查询courier表查询配送员其他信息
        CourierInfo courierInfo = courierMapper.selectById(userDelivery.getCourierId());
        //封装数据到CourierAddressVo
        CourierAddressVo courierAddressVo = new CourierAddressVo();
        BeanUtils.copyProperties(courierInfo, courierAddressVo);
        courierAddressVo.setUserId(userId);
        courierAddressVo.setCourierId(courierInfo.getId());
        courierAddressVo.setCourierName(courierInfo.getName());
        courierAddressVo.setCourierPhone(courierInfo.getPhone());
        courierAddressVo.setWareId(userDelivery.getWareId());
        return courierAddressVo;
    }

    @Override
    public AddressVo getAddressByUserId(Long userId) {
        // 根据userId查询用户默认的地址id
        Address address = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getUserId, userId)
                        .eq(Address::getIsDefault, 1)
        );
        if (address == null) {
            return null;
        }
        // 封装数据到 AddressVo
        AddressVo addressVo = new AddressVo();
        BeanUtils.copyProperties(address, addressVo);
        return addressVo;
    }

    //7 获取当前登录用户信息，
    @Override
    public UserVo getUserLoginVo(Long id) {
        User user = baseMapper.selectById(id);
        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);
        if (user.getUserType() == UserType.COURIER) {
            userVo.setCourierInfo(courierMapper.selectOne(new LambdaQueryWrapper<CourierInfo>()
                    .eq(CourierInfo::getUserId, user.getId())
            ));
        }
//        userVo.setUserId(id);
//        userVo.setNickName(user.getNickName());
//        userVo.setAvatar(user.getAvatar());
//        userVo.setIsNew(user.getIsNew());
//        userVo.setOpenId(user.getOpenId());

//        UserDelivery userDelivery = userDeliveryMapper.selectOne(
//                new LambdaQueryWrapper<UserDelivery>().eq(UserDelivery::getUserId, id)
//                        .eq(UserDelivery::getIsDefault, 1)
//        );
        //根据userId查询用户默认的地址id
        Address address = addressMapper.selectOne(
                new LambdaQueryWrapper<Address>()
                        .eq(Address::getUserId, id)
                        .eq(Address::getIsDefault, 1)
        );
        if (address != null) {
            userVo.setStationId(address.getStationId());
            userVo.setWareId(address.getWareId());
        } else {
            userVo.setStationId(1L);
            userVo.setWareId(1L);
        }
        return userVo;
    }
}
