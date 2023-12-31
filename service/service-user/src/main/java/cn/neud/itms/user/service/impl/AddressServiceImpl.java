package cn.neud.itms.user.service.impl;

import cn.neud.itms.model.user.Address;
import cn.neud.itms.user.mapper.AddressMapper;
import cn.neud.itms.user.service.AddressService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Service
public class AddressServiceImpl extends ServiceImpl<AddressMapper, Address> implements AddressService {

    //根据平台属性分组id查询
    @Override
    public List<Address> getAddressListByUserId(Long userId) {
        return baseMapper.selectList(new LambdaQueryWrapper<Address>()
                .eq(Address::getUserId, userId)
        );
    }

    @Override
    public void setAllUnDefault() {
        baseMapper.selectList(new LambdaQueryWrapper<Address>()
                .eq(Address::getIsDefault, 1)
        ).forEach(address -> {
            address.setIsDefault(0);
            baseMapper.updateById(address);
        });
    }
}
