package cn.neud.itms.search.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.search.SkuEs;
import cn.neud.itms.search.service.SkuService;
import cn.neud.itms.vo.search.SkuEsQueryVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(tags = "商品检索")
@RestController
@RequestMapping("/api/search/sku")
public class SkuApiController {

    @Autowired
    private SkuService skuService;

    // 查询分类商品
    @ApiOperation(value = "查询分类商品")
    @PostMapping("{page}/{limit}")
    public Result listSku(
            @PathVariable Integer page,
            @PathVariable Integer limit,
            @RequestBody SkuEsQueryVo skuEsQueryVo
    ) {
        //创建pageable对象，0代表第一页
        Pageable pageable = PageRequest.of(page - 1, limit);
        Page<SkuEs> pageModel = skuService.search(pageable, skuEsQueryVo);
        return Result.ok(pageModel);
    }

    // 上架
    @ApiOperation(value = "上架")
    @GetMapping("inner/upperSku/{skuId}")
    public Result upperSku(@PathVariable Long skuId) {
        skuService.upperSku(skuId);
        return Result.ok(null);
    }

    // 下架
    @ApiOperation(value = "下架")
    @GetMapping("inner/lowerSku/{skuId}")
    public Result lowerSku(@PathVariable Long skuId) {
        skuService.lowerSku(skuId);
        return Result.ok(null);
    }

    // 获取爆款商品
    @GetMapping("inner/findHotSkuList")
    public List<SkuEs> findHotSkuList() {
        return skuService.findHotSkuList();
    }

    // 更新商品热度
    @GetMapping("inner/incrHotScore/{skuId}")
    public Boolean incrHotScore(@PathVariable("skuId") Long skuId) {
        skuService.incrHotScore(skuId);
        return true;
    }
}
