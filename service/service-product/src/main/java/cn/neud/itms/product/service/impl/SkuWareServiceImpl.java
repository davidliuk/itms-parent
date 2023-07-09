package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.SkuWare;
import cn.neud.itms.product.mapper.SkuWareMapper;
import cn.neud.itms.product.service.SkuWareService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * spu属性值 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@Service
public class SkuWareServiceImpl extends ServiceImpl<SkuWareMapper, SkuWare> implements SkuWareService {

    //根据id查询商品属性信息列表
    @Override
    public List<SkuWare> getSkuWareListBySkuId(Long id) {
        LambdaQueryWrapper<SkuWare> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SkuWare::getSkuId, id);
        return baseMapper.selectList(wrapper);
    }

    @Override
    public IPage<SkuWare> selectPage(Page<SkuWare> pageParam, SkuWare skuWare) {
        Long id = skuWare.getId();
        Long wareId = skuWare.getWareId();
        Long skuId = skuWare.getSkuId();
        String skuName = skuWare.getSkuName();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<SkuWare>()
                .eq(!StringUtils.isEmpty(id), SkuWare::getId, id)
                .eq(!StringUtils.isEmpty(wareId), SkuWare::getWareId, wareId)
                .eq(!StringUtils.isEmpty(skuId), SkuWare::getSkuId, skuId)
                .like(!StringUtils.isEmpty(skuName), SkuWare::getSkuName, skuName)
        );
    }

    @Override
    public Map<String, Object> selectStockByIds(Long[] ids) {
        return baseMapper.selectStockByIds(ids);
    }
}
