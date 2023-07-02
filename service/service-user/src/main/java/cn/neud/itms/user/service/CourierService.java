package cn.neud.itms.user.service;

import cn.neud.itms.model.user.Address;
import cn.neud.itms.model.user.CourierInfo;
import cn.neud.itms.model.user.User;
import cn.neud.itms.vo.user.CourierQueryVo;
import cn.neud.itms.vo.user.UserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
public interface CourierService extends IService<CourierInfo> {

    IPage<CourierInfo> selectPage(Page<CourierInfo> pageParam, CourierQueryVo courierQueryVo);

}
