package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Ware;
import cn.neud.itms.sys.mapper.WareMapper;
import cn.neud.itms.sys.service.WareService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Service
public class WareServiceImpl extends ServiceImpl<WareMapper, Ware> implements WareService {

    @Override
    public IPage<Ware> selectPage(Page<Ware> pageParam, Ware ware) {
        Long id = ware.getId();
        String name = ware.getName();
        String province = ware.getProvince();
        String city = ware.getCity();
        String district = ware.getDistrict();
        String detailAddress = ware.getDetailAddress();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Ware>()
                .eq(!StringUtils.isEmpty(id), Ware::getId, id)
                .like(!StringUtils.isEmpty(name), Ware::getName, name)
                .like(!StringUtils.isEmpty(province), Ware::getProvince, province)
                .like(!StringUtils.isEmpty(city), Ware::getCity, city)
                .like(!StringUtils.isEmpty(district), Ware::getDistrict, district)
                .like(!StringUtils.isEmpty(detailAddress), Ware::getDetailAddress, detailAddress)
        );
    }
}
