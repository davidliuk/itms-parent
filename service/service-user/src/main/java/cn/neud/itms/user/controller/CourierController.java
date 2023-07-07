package cn.neud.itms.user.controller;

import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.user.CourierInfo;
import cn.neud.itms.user.service.CourierService;
import cn.neud.itms.vo.user.CourierQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin/user/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;

    // 根据配送员id分页查询配送任务单
    @ApiOperation(value = "分页条件查询")
    @PostMapping("/{current}/{limit}")
    public Result page(
            @PathVariable("current") Long current,
            @PathVariable("limit") Long limit,
            CourierQueryVo courierQueryVo
    ) {
        Page<CourierInfo> pageParam = new Page<>(current, limit);
        return Result.ok(courierService.selectPage(pageParam, courierQueryVo));
    }

    @ApiOperation(value = "修改")
    @PutMapping()
    public Result update(@RequestBody CourierInfo courierInfo) {
        courierService.updateById(courierInfo);
        return Result.ok(null);
    }

    @ApiOperation(value = "删除")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        courierService.removeById(id);
        return Result.ok(null);
    }
}
