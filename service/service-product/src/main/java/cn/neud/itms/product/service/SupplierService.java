package cn.neud.itms.product.service;

import cn.neud.itms.model.product.Supplier;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 属性分组 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
public interface SupplierService extends IService<Supplier> {

    //平台属性分组列表
    IPage<Supplier> selectPage(Page<Supplier> pageParam, Supplier supplier);

}
