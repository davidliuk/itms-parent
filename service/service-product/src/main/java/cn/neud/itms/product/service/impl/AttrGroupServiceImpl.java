package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.AttrGroup;
import cn.neud.itms.product.mapper.AttrGroupMapper;
import cn.neud.itms.product.service.AttrGroupService;
import cn.neud.itms.vo.product.AttrGroupQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 属性分组 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Service
public class AttrGroupServiceImpl extends ServiceImpl<AttrGroupMapper, AttrGroup> implements AttrGroupService {

    //平台属性分组列表
    @Override
    public IPage<AttrGroup> selectPageAttrGroup(Page<AttrGroup> pageParam, AttrGroupQueryVo attrGroupQueryVo) {
        Long id = attrGroupQueryVo.getId();
        String name = attrGroupQueryVo.getName();
        String remark = attrGroupQueryVo.getRemark();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<AttrGroup>()
                .eq(!StringUtils.isEmpty(id), AttrGroup::getId, id)
                .like(!StringUtils.isEmpty(name), AttrGroup::getName, name)
                .like(!StringUtils.isEmpty(remark), AttrGroup::getRemark, remark)
        );
    }

    //查询所有平台属性分组列表
    @Override
    public List<AttrGroup> findAllListAttrGroup() {
        //LambdaQueryWrapper<AttrGroup> wrapper = new LambdaQueryWrapper<>();
        QueryWrapper<AttrGroup> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        return baseMapper.selectList(wrapper);
    }
}
