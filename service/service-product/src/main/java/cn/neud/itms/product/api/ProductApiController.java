package cn.neud.itms.product.api;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.product.Category;
import cn.neud.itms.model.product.SkuInfo;
import cn.neud.itms.product.service.CategoryService;
import cn.neud.itms.product.service.SkuInfoService;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.vo.product.SkuInfoVo;
import cn.neud.itms.vo.product.SkuStockLockVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/product")
public class ProductApiController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private RedisTemplate redisTemplate;

    @ApiOperation("获取sku信息")
    @GetMapping("/skuInfo/{id}")
    public Result get(@PathVariable Long id) {
        SkuInfoVo skuInfoVo = skuInfoService.getSkuInfo(id);
        return Result.ok(skuInfoVo);
    }

    // 查询所有商品分类
    @ApiOperation("查询所有商品分类")
    @GetMapping("findAllList")
    public Result findAllList() {
        // 用redis来写
        if (!redisTemplate.hasKey(RedisConstant.CATEGORY_KEY_PREFIX + "all")) {
            redisTemplate.opsForValue().set(RedisConstant.CATEGORY_KEY_PREFIX + "all",
                    categoryService.list(new LambdaQueryWrapper<Category>()
                            .eq(Category::getParentId, 0)),
                    RedisConstant.CATEGORY_KEY_TIMEOUT,
                    TimeUnit.SECONDS
            );
        }
        return Result.ok(redisTemplate.opsForValue().get(RedisConstant.CATEGORY_KEY_PREFIX + "all"));
    }

    //根据分类id获取分类信息
    @GetMapping("inner/getCategory/{categoryId}")
    public Category getCategory(@PathVariable Long categoryId) {
        return categoryService.getById(categoryId);
    }

    //根据skuId获取sku信息
    @GetMapping("inner/getSkuInfo/{skuId}")
    public SkuInfo getSkuInfo(@PathVariable Long skuId) {
        return skuInfoService.getById(skuId);
    }

    //根据skuId列表得到sku信息列表
    @PostMapping("inner/findSkuInfoList")
    public List<SkuInfo> findSkuInfoList(@RequestBody List<Long> skuIdList) {
        return skuInfoService.findSkuInfoList(skuIdList);
    }

    //根据分类id获取分类列表
    @PostMapping("inner/findCategoryList")
    public List<Category> findCategoryList(@RequestBody List<Long> categoryIdList) {
        return categoryService.listByIds(categoryIdList);
    }

    //根据关键字匹配sku列表
    @GetMapping("inner/findSkuInfoByKeyword/{keyword}")
    public List<SkuInfo> findSkuInfoByKeyword(@PathVariable("keyword") String keyword) {
        return skuInfoService.findSkuInfoByKeyword(keyword);
    }

    //获取所有分类
    @GetMapping("inner/findAllCategoryList")
    public List<Category> findAllCategoryList() {
        return categoryService.list();
    }

    // 获取新人专享商品
    @GetMapping("inner/findNewPersonSkuInfoList")
    public List<SkuInfo> findNewPersonSkuInfoList() {
        return skuInfoService.findNewPersonSkuInfoList();
    }

    // 获取新人专享商品
    @GetMapping("inner/findNewSkuInfoList")
    public List<SkuInfo> findNewSkuInfoList() {
        return skuInfoService.findNewSkuInfoList();
    }

    // 根据skuId获取sku信息
    @GetMapping("inner/getSkuInfoVo/{skuId}")
    public SkuInfoVo getSkuInfoVo(@PathVariable Long skuId) {
        return skuInfoService.getSkuInfoVo(skuId);
    }

    // 验证和锁定库存
    @ApiOperation(value = "锁定库存")
    @PostMapping("inner/checkAndLock/{wareId}/{orderNo}")
    public Boolean checkAndLock(
            @RequestBody List<SkuStockLockVo> skuStockLockVoList,
            @PathVariable Long wareId,
            @PathVariable String orderNo
    ) {
        return skuInfoService.checkAndLock(skuStockLockVoList, wareId, orderNo);
    }
}
