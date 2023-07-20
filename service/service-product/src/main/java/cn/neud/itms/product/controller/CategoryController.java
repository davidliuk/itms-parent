package cn.neud.itms.product.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.product.Category;
import cn.neud.itms.product.service.CategoryService;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.vo.product.CategoryQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * <p>
 * 商品三级分类 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-04-04
 */
@Api(tags = "商品类别管理")
@RestController
@RequestMapping("/admin/product/category")
//@CrossOrigin
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private RedisTemplate redisTemplate;

    //商品分类列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("商品分类列表")
    @PostMapping("{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            CategoryQueryVo categoryQueryVo
    ) {
        Page<Category> pageParam = new Page<>(page, limit);
        IPage<Category> pageModel = categoryService.selectPageCategory(pageParam, categoryQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation(value = "获取商品分类信息")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        if (!redisTemplate.hasKey(RedisConstant.CATEGORY_KEY_PREFIX + id)) {
            redisTemplate.opsForValue().set(RedisConstant.CATEGORY_KEY_PREFIX + id,
                    categoryService.getById(id),
                    RedisConstant.CATEGORY_KEY_TIMEOUT,
                    TimeUnit.SECONDS
            );
        }
        return Result.ok(redisTemplate.opsForValue().get(RedisConstant.CATEGORY_KEY_PREFIX + id));
    }

    @ApiOperation(value = "新增商品分类")
    @PostMapping("")
    public Result save(@RequestBody Category category) {
        categoryService.save(category);
        redisTemplate.delete(RedisConstant.CATEGORY_KEY_PREFIX + "all");
        return Result.ok(null);
    }

    @ApiOperation(value = "修改商品分类")
    @PutMapping("")
    public Result updateById(@RequestBody Category category) {
        categoryService.updateById(category);
        redisTemplate.delete(RedisConstant.CATEGORY_KEY_PREFIX + "all");
        return Result.ok(null);
    }

    @ApiOperation(value = "删除商品分类")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        categoryService.removeById(id);
        redisTemplate.delete(RedisConstant.CATEGORY_KEY_PREFIX + "all");
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除商品分类")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        categoryService.removeByIds(idList);
        redisTemplate.delete(RedisConstant.CATEGORY_KEY_PREFIX + "all");
        return Result.ok(null);
    }

    //      url: `${api_name}/findAllList`,
    //      method: 'get'
    // 查询所有商品分类
    @ApiOperation("查询所有商品分类")
    @GetMapping("findAllList")
    public Result findAllList() {
        // 用redis来写
        if (!redisTemplate.hasKey(RedisConstant.CATEGORY_KEY_PREFIX + "all")) {
            redisTemplate.opsForValue().set(RedisConstant.CATEGORY_KEY_PREFIX + "all",
                    categoryService.list(),
                    RedisConstant.CATEGORY_KEY_TIMEOUT,
                    TimeUnit.SECONDS
            );
        }

        return Result.ok(redisTemplate.opsForValue()
                .get(RedisConstant.CATEGORY_KEY_PREFIX + "all"));
    }
}
