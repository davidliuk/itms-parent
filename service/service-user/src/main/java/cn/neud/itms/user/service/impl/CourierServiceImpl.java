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
import cn.neud.itms.user.service.CourierService;
import cn.neud.itms.user.service.UserService;
import cn.neud.itms.vo.user.*;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class CourierServiceImpl extends ServiceImpl<CourierMapper, CourierInfo> implements CourierService {

    @Override
    public IPage<CourierInfo> selectPage(Page<CourierInfo> pageParam, CourierQueryVo courierQueryVo) {
        //获取条件值
        Long stationId = courierQueryVo.getStationId();
        String name = courierQueryVo.getName();
        String idNo = courierQueryVo.getIdNo();

        // 调用方法实现条件分页查询
        // 返回分页对象
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<CourierInfo>()
                .eq(stationId != null, CourierInfo::getStationId, stationId)
                .like(!StringUtils.isEmpty(name), CourierInfo::getName, name)
                .like(!StringUtils.isEmpty(idNo), CourierInfo::getIdNo, idNo));
    }

}
