package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.product.Attr;
import cn.neud.itms.model.sys.RegionStation;
import cn.neud.itms.sys.mapper.RegionStationMapper;
import cn.neud.itms.sys.service.RegionStationService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 地区表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
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
}
