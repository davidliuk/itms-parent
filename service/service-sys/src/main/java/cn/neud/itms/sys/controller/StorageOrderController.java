package cn.neud.itms.sys.controller;


import cn.neud.itms.common.result.Result;
import cn.neud.itms.enums.OrderStatus;
import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.sys.service.StorageOrderService;
import cn.neud.itms.vo.sys.StorageOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 城市仓库关联表 前端控制器
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Api(tags = "出入库管理")
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
    @GetMapping("/{page}/{limit}")
    public Result list(
            @PathVariable Long page,
            @PathVariable Long limit,
            StorageOrderQueryVo workOrderQueryVo
    ) {
        Page<StorageOrder> pageParam = new Page<>(page, limit);
        IPage<StorageOrder> pageModel = storageOrderService.selectPage(pageParam, workOrderQueryVo);
        return Result.ok(pageModel);
    }

    // 保存库存单
    

}

