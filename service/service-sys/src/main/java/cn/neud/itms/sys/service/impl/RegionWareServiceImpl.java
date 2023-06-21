package cn.neud.itms.sys.service.impl;

import cn.neud.itms.sys.service.RegionWareService;
import cn.neud.itms.common.exception.ItmsException;
import cn.neud.itms.common.result.ResultCodeEnum;
import cn.neud.itms.model.sys.RegionWare;
import cn.neud.itms.sys.mapper.RegionWareMapper;
import cn.neud.itms.vo.sys.RegionWareQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Service
public class RegionWareServiceImpl extends ServiceImpl<RegionWareMapper, RegionWare> implements RegionWareService {

    // 开通区域列表
    @Override
    public IPage<RegionWare> selectPageRegionWare(Page<RegionWare> pageParam,
                                                  RegionWareQueryVo regionWareQueryVo) {
        //1 获取查询条件值
        String keyword = regionWareQueryVo.getKeyword();

        //2 判断条件值是否为空，不为空封装条件
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        if (!StringUtils.isEmpty(keyword)) {
            //封装条件
            //根据区域名称 或者 仓库名称查询
            wrapper.like(RegionWare::getRegionName, keyword)
                    .or().like(RegionWare::getWareName, keyword);
        }

        //3 调用方法实现条件分页查询
        //4 数据返回
        return baseMapper.selectPage(pageParam, wrapper);
    }

    //添加开通区域
    @Override
    public void saveRegionWare(RegionWare regionWare) {
        //判断区域是否已经开通了
        LambdaQueryWrapper<RegionWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RegionWare::getRegionId, regionWare.getRegionId());
        Integer count = baseMapper.selectCount(wrapper);
        if (count > 0) { //已经存在
            //抛出自定义异常
            throw new ItmsException(ResultCodeEnum.REGION_OPEN);
        }
        baseMapper.insert(regionWare);
    }

    //取消开通区域
    @Override
    public void updateStatus(Long id, Integer status) {
        RegionWare regionWare = baseMapper.selectById(id);
        regionWare.setStatus(status);
        baseMapper.updateById(regionWare);
    }
}
