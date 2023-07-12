package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.Category;
import cn.neud.itms.product.mapper.CategoryMapper;
import cn.neud.itms.product.service.CategoryService;
import cn.neud.itms.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 商品三级分类 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    //商品分类列表
    @Override
    public IPage<Category> selectPageCategory(Page<Category> pageParam,
                                              CategoryQueryVo categoryQueryVo) {
        String name = categoryQueryVo.getName();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Category>()
                .like(!StringUtils.isEmpty(name), Category::getName, name)
        );
    }
}
