package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.RegionStation;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 地区表 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
public interface RegionStationService extends IService<RegionStation> {

    //根据区域关键字查询区域列表信息
    List<RegionStation> getRegionStationByKeyword(String keyword);

    List<RegionStation> getRegionStationListByGroupId(Long regionId);

    IPage<RegionStation> selectRole(Page<RegionStation> pageParam, RegionStation regionStation);
}
