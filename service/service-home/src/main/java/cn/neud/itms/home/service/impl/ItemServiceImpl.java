package cn.neud.itms.home.service.impl;

import cn.neud.itms.activity.client.ActivityFeignClient;
import cn.neud.itms.client.product.ProductFeignClient;
import cn.neud.itms.client.search.SkuFeignClient;
import cn.neud.itms.home.service.ItemService;
import cn.neud.itms.vo.product.SkuInfoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private ActivityFeignClient activityFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    //详情A
    @Override
    public Map<String, Object> item(Long skuId) {
        Map<String, Object> result = new HashMap<>();

        // skuId 查询
        CompletableFuture<SkuInfoVo> skuInfocompletableFuture =
                CompletableFuture.supplyAsync(() -> {
                    //远程调用获取sku对应数据
                    SkuInfoVo skuInfoVo = productFeignClient.getSkuInfoVo(skuId);
                    result.put("skuInfoVo", skuInfoVo);
                    return skuInfoVo;
                }, threadPoolExecutor);

        // 更新商品热度
        CompletableFuture<Void> hotCompletableFuture = CompletableFuture.runAsync(() -> {
            // 远程调用更新热度
            skuFeignClient.incrHotScore(skuId);
        }, threadPoolExecutor);

        // 任务组合
        CompletableFuture.allOf(
                skuInfocompletableFuture,
                hotCompletableFuture
        ).join();

        return result;
    }
}
