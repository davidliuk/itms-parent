package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.RegionStation;
import cn.neud.itms.sys.mapper.RegionStationMapper;
import cn.neud.itms.sys.service.RegionStationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Service
public class RegionStationServiceImpl extends ServiceImpl<RegionStationMapper, RegionStation> implements RegionStationService {

    //根据区域关键字查询区域列表信息
    @Override
    public List<RegionStation> getRegionStationByKeyword(String keyword) {
        LambdaQueryWrapper<RegionStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(RegionStation::getName, keyword);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public List<RegionStation> getRegionStationListByGroupId(Long regionId) {
        LambdaQueryWrapper<RegionStation> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionStation::getRegionId, regionId);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<RegionStation> selectPage(Page<RegionStation> pageParam, RegionStation regionStation) {
        Long id = regionStation.getId();
        Long wareId = regionStation.getWareId();
        String province = regionStation.getProvince();
        String city = regionStation.getCity();
        String district = regionStation.getDistrict();
        String detailAddress = regionStation.getDetailAddress();
        String name = regionStation.getName();
        String phone = regionStation.getPhone();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<RegionStation>()
                .eq(!StringUtils.isEmpty(id), RegionStation::getId, id)
                .eq(!StringUtils.isEmpty(wareId), RegionStation::getWareId, wareId)
                .like(!StringUtils.isEmpty(province), RegionStation::getProvince, province)
                .like(!StringUtils.isEmpty(city), RegionStation::getCity, city)
                .like(!StringUtils.isEmpty(district), RegionStation::getDistrict, district)
                .like(!StringUtils.isEmpty(detailAddress), RegionStation::getDetailAddress, detailAddress)
                .like(!StringUtils.isEmpty(name), RegionStation::getName, name)
                .like(!StringUtils.isEmpty(phone), RegionStation::getPhone, phone)
        );
    }
}
