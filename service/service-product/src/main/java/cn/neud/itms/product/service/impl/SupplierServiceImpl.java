package cn.neud.itms.product.service.impl;

import cn.neud.itms.model.product.Supplier;
import cn.neud.itms.product.mapper.SupplierMapper;
import cn.neud.itms.product.service.SupplierService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 商品属性 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@Service
public class SupplierServiceImpl extends ServiceImpl<SupplierMapper, Supplier> implements SupplierService {

    @Override
    public IPage<Supplier> selectPage(Page<Supplier> pageParam, Supplier supplier) {
        String name = supplier.getName();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Supplier>()
                .like(!StringUtils.isEmpty(name), Supplier::getName, name)
        );
    }
}
