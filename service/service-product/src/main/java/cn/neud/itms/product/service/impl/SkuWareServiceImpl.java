package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.SkuWare;
import cn.neud.itms.product.mapper.SkuWareMapper;
import cn.neud.itms.product.service.SkuWareService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

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
}
