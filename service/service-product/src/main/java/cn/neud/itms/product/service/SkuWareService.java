package cn.neud.itms.product.service;

import cn.neud.itms.model.product.SkuWare;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * spu属性值 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
public interface SkuWareService extends IService<SkuWare> {

    //根据id查询商品属性信息列表
    List<SkuWare> getSkuWareListBySkuId(Long id);

    IPage<SkuWare> selectPage(Page<SkuWare> pageParam, SkuWare skuWare);
}
