package cn.neud.itms.product.service;

import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.vo.product.SkuInfoQueryVo;
import cn.neud.itms.vo.product.SkuInfoVo;
import cn.neud.itms.vo.product.SkuStockLockVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * sku信息 服务类
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
public interface SkuInfoService extends IService<SkuInfo> {

    //sku列表
    IPage<SkuInfo> selectPageSkuInfo(Page<SkuInfo> pageParam, SkuInfoQueryVo skuInfoQueryVo);

    //添加商品sku信息
    void saveSkuInfo(SkuInfoVo skuInfoVo);

    //获取sku信息
    SkuInfoVo getSkuInfo(Long id);

    //修改sku
    void updateSkuInfo(SkuInfoVo skuInfoVo);

    //商品审核
    void check(Long skuId, Integer status);

    //商品上下架
    void publish(Long skuId, Integer status);

    //新人专享
    void isNewPerson(Long skuId, Integer status);

    //根据skuId列表得到sku信息列表
    List<SkuInfo> findSkuInfoList(List<Long> skuIdList);

    //根据关键字匹配sku列表
    List<SkuInfo> findSkuInfoByKeyword(String keyword);

    //获取新人专享商品
    List<SkuInfo> findNewPersonSkuInfoList();

    //根据skuId获取sku信息
    SkuInfoVo getSkuInfoVo(Long skuId);

    //验证和锁定库存
    Boolean checkAndLock(List<SkuStockLockVo> skuStockLockVoList, Long wareId, String orderNo);

    //扣减库存成功，更新订单状态
    void minusStock(Long wareId, String orderNo);

    void addStock(Long wareId, Long skuId, int skuNum);

    List<SkuInfo> findNewSkuInfoList();
}
