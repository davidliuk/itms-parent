package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.Attr;
import cn.neud.itms.product.mapper.AttrMapper;
import cn.neud.itms.product.service.AttrService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@Service
public class AttrServiceImpl extends ServiceImpl<AttrMapper, Attr> implements AttrService {

    //根据平台属性分组id查询
    @Override
    public List<Attr> getAttrListByGroupId(Long groupId) {
        return baseMapper.selectList(new LambdaQueryWrapper<Attr>()
                .eq(Attr::getAttrGroupId, groupId)
        );
    }
}
