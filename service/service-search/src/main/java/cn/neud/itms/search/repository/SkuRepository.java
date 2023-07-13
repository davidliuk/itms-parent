package cn.neud.itms.search.repository;

import cn.neud.itms.model.search.SkuEs;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface SkuRepository extends ElasticsearchRepository<SkuEs, Long> {

    // 获取爆款商品
    Page<SkuEs> findByOrderByHotScoreDesc(Pageable pageable);

    // 判断 keyword 是否为空，如果为空 ，根据仓库 id + 分类 id 查询
    Page<SkuEs> findByCategoryIdAndWareId(Long categoryId, Long wareId, Pageable pageable);

    // 如果 keyword 不为空根据仓库 id + keyword 进行查询
    Page<SkuEs> findByKeywordAndWareId(String keyword, Long wareId, Pageable pageable);
    // List<SkuEs> findByOrderByHotScoreDesc();
}
