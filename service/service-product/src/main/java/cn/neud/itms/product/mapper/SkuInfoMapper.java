package cn.neud.itms.product.mapper;

import cn.neud.itms.model.product.SkuInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 * sku信息 Mapper 接口
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
public interface SkuInfoMapper extends BaseMapper<SkuInfo> {

    //解锁库存
    void unlockStock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //验证库存
    SkuInfo checkStock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //锁定库存:update
    Integer lockStock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    //遍历集合，得到每个对象，减库存
    void minusStock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);

    void addStock(@Param("wareId") Long wareId, @Param("skuId") Long skuId, @Param("skuNum") Integer skuNum);
}
