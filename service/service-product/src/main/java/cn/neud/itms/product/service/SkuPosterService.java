package cn.neud.itms.product.service;

import cn.neud.itms.model.product.SkuPoster;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 商品海报表 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
public interface SkuPosterService extends IService<SkuPoster> {

    //根据id查询商品海报列表
    List<SkuPoster> getPosterListBySkuId(Long id);
}
