package cn.neud.itms.home.service.impl;

import cn.neud.itms.client.product.ProductFeignClient;
import cn.neud.itms.client.search.SkuFeignClient;
import cn.neud.itms.client.user.UserFeignClient;
import cn.neud.itms.home.service.HomeService;
import cn.neud.itms.model.product.Category;
import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.model.search.SkuEs;
import cn.neud.itms.vo.product.SkuInfoVo;
import cn.neud.itms.vo.user.AddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    //首页数据显示接口
    @Override
    public Map<String, Object> homeData(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        CompletableFuture<Void> addressVoCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    AddressVo addressVo = userFeignClient.getAddressByUserId(userId);
                    result.put("addressVo", addressVo);
                }, threadPoolExecutor);

        CompletableFuture<Void> posterCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    AddressVo addressVo = userFeignClient.getAddressByUserId(userId);
                    result.put("addressVo", addressVo);
                }, threadPoolExecutor);

        //2 获取所有分类
        // 远程调用service-product模块接口
        CompletableFuture<Void> categoryListCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    List<Category> categoryList = productFeignClient.findAllCategoryList();
                    result.put("categoryList", categoryList);
                }, threadPoolExecutor);

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        CompletableFuture<Void> newPersonSkuInfoListCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
                    result.put("newPersonSkuList", newPersonSkuInfoList);
                }, threadPoolExecutor);

        //4 获取新商品
        // 远程调用service-product模块接口
        CompletableFuture<Void> newSkuInfoListCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    List<SkuInfo> newSkuInfoList = productFeignClient.findNewSkuInfoList();
                    result.put("newSkuList", newSkuInfoList);
                }, threadPoolExecutor);

        //5 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        CompletableFuture<Void> hotSkuListCompletableFuture =
                CompletableFuture.runAsync(() -> {
                    List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
                    result.put("hotSkuList", hotSkuList);
                }, threadPoolExecutor);

        // 任务组合
        CompletableFuture.allOf(
                addressVoCompletableFuture,
                categoryListCompletableFuture,
                newPersonSkuInfoListCompletableFuture,
                newSkuInfoListCompletableFuture,
                hotSkuListCompletableFuture
        ).join();

        //5 封装获取数据到map集合，返回
        return result;
    }
}
