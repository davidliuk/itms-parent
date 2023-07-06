package cn.neud.itms.user.controller;

import cn.neud.itms.common.auth.SaUserCheckLogin;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.user.CourierInfo;
import cn.neud.itms.user.service.CourierService;
import cn.neud.itms.vo.user.CourierQueryVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin/user/courier")
public class CourierController {

    @Autowired
    private CourierService courierService;

    // 根据配送员id分页查询配送任务单
    @SaUserCheckLogin
    @PostMapping("/{current}/{limit}")
    public Result page(
            @PathVariable("current") Long current,
            @PathVariable("limit") Long limit,
            CourierQueryVo courierQueryVo
    ) {
        Page<CourierInfo> pageParam = new Page<>(current, limit);
        return Result.ok(courierService.selectPage(pageParam, courierQueryVo));
    }
}