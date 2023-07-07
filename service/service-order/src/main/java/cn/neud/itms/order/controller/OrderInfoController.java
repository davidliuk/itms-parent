package cn.neud.itms.order.controller;

import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.order.service.OrderInfoService;
import cn.neud.itms.vo.order.OrderInfoQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController("/admin/order")
public class OrderInfoController {

    @Autowired
    private OrderInfoService orderInfoService;

    @ApiOperation(value = "分页条件查询")
    @PostMapping("/{current}/{limit}")
    public Result page(
            @PathVariable("current") Long current,
            @PathVariable("limit") Long limit,
            OrderInfoQueryVo orderInfoQueryVo
    ) {
        Page<OrderInfo> pageParam = new Page<>(current, limit);
        return Result.ok(orderInfoService.selectPage(pageParam, orderInfoQueryVo));
    }

    @GetMapping("{id}")
    public Result getDetail(@PathVariable Long id) {
        orderInfoService.getOrderInfoDetail(id);
        return Result.ok(null);
    }

    @PutMapping
    public Result update(@RequestBody OrderInfo orderInfo) {
        orderInfoService.updateById(orderInfo);
        return Result.ok(null);
    }

    @DeleteMapping("{id}")
    public Result delete(@PathVariable Long id) {
        orderInfoService.removeById(id);
        return Result.ok(null);
    }

}
