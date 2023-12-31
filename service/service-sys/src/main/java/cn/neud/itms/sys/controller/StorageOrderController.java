package cn.neud.itms.sys.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.StorageOrderService;
import cn.neud.itms.vo.sys.StorageOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Api(tags = "库存单管理")
@RestController
@RequestMapping("/admin/sys/storageOrder")
//@CrossOrigin
public class StorageOrderController {

    @Autowired
    private StorageOrderService storageOrderService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    //开通区域列表
//    url: `${api_name}/${page}/${limit}`,
//    method: 'get',
//    params: searchObj
    @ApiOperation("库存单列表")
    @PostMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            @RequestBody StorageOrderQueryVo workOrderQueryVo
    ) {
        Page<StorageOrder> pageParam = new Page<>(page, limit);
        IPage<StorageOrder> pageModel = storageOrderService.selectPage(pageParam, workOrderQueryVo);
        return Result.ok(pageModel);
    }


    @ApiOperation(value = "获取")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        StorageOrder storageOrder = storageOrderService.getById(id);
        return Result.ok(storageOrder);
    }

    @ApiOperation(value = "新增")
    @PostMapping("")
    public Result save(@RequestBody StorageOrder storageOrder) {
        storageOrderService.save(storageOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "修改")
    @PutMapping("")
    public Result updateById(@RequestBody StorageOrder storageOrder) {
        storageOrderService.updateById(storageOrder);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        storageOrderService.removeById(id);
        return Result.ok(null);
    }

    @ApiOperation(value = "根据id列表删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        storageOrderService.removeByIds(idList);
        return Result.ok(null);
    }

}

