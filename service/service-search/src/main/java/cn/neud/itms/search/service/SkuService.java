package cn.neud.itms.search.service;

import cn.neud.itms.model.search.SkuEs;
import cn.neud.itms.vo.search.SkuEsQueryVo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface SkuService {

    //上架
    void upperSku(Long skuId);

    //下架
    void lowerSku(Long skuId);

    //获取爆款商品
    List<SkuEs> findHotSkuList();

    //查询分类商品
    Page<SkuEs> search(Pageable pageable, SkuEsQueryVo skuEsQueryVo);

    //更新商品热度
    void incrHotScore(Long skuId);
}
