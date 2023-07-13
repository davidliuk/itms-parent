package cn.neud.itms.home.service.impl;

import cn.neud.itms.client.product.ProductFeignClient;
import cn.neud.itms.client.search.SkuFeignClient;
import cn.neud.itms.client.user.UserFeignClient;
import cn.neud.itms.home.service.HomeService;
import cn.neud.itms.model.product.Category;
import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.model.search.SkuEs;
import cn.neud.itms.vo.user.AddressVo;
import cn.neud.itms.vo.user.CourierAddressVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class HomeServiceImpl implements HomeService {

    @Autowired
    private UserFeignClient userFeignClient;

    @Autowired
    private ProductFeignClient productFeignClient;

    @Autowired
    private SkuFeignClient skuFeignClient;

    //首页数据显示接口
    @Override
    public Map<String, Object> homeData(Long userId) {
        Map<String, Object> result = new HashMap<>();
        // 1 根据userId获取当前登录用户提货地址信息
        // 远程调用service-user模块接口获取需要数据
        CourierAddressVo courierAddressVo = userFeignClient.getUserAddressByUserId(userId);
        result.put("courierAddressVo", courierAddressVo);
        // 远程调用service-user模块接口获取需要数据
        AddressVo addressVo = userFeignClient.getAddressByUserId(userId);
        result.put("addressVo", addressVo);

        //2 获取所有分类
        // 远程调用service-product模块接口
        List<Category> categoryList = productFeignClient.findAllCategoryList();
        result.put("categoryList", categoryList);

        //3 获取新人专享商品
        // 远程调用service-product模块接口
        List<SkuInfo> newPersonSkuInfoList = productFeignClient.findNewPersonSkuInfoList();
        result.put("newPersonSkuInfoList", newPersonSkuInfoList);

        //4 获取爆款商品
        // 远程调用service-search模块接口
        // hotscore 热门评分降序排序
        List<SkuEs> hotSkuList = skuFeignClient.findHotSkuList();
        result.put("hotSkuList", hotSkuList);

        //5 封装获取数据到map集合，返回
        return result;
    }
}
