package cn.neud.itms.product.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.product.SkuWare;
import cn.neud.itms.product.service.SkuWareService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * sku信息 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-04
 */
@Api(tags = "商品库存管理")
@RestController
@RequestMapping("/admin/product/skuWare")
//@CrossOrigin
public class SkuWareController {

    @Autowired
    private SkuWareService skuWareService;

    // sku列表
    //    url: `${api_name}/${page}/${limit}`,
    //    method: 'get',
    //    params: searchObj
    @ApiOperation("sku库存列表")
    @PostMapping("{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            SkuWare skuWare
    ) {
        Page<SkuWare> pageParam = new Page<>(page, limit);
        IPage<SkuWare> pageModel = skuWareService.selectPage(pageParam, skuWare);
        return Result.ok(pageModel);
    }

}
