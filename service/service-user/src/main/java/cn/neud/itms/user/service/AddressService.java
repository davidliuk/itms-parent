package cn.neud.itms.user.service;

import cn.neud.itms.model.user.Address;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
public interface AddressService extends IService<Address> {

    //根据平用户id查询
    List<Address> getAddressListByUserId(Long userId);

    void setAllUnDefault();
}
