package cn.neud.itms.acl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.neud.itms.acl.service.AdminService;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.vo.user.PasswordLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin //跨域
public class IndexController {

    @Autowired
    AdminService adminService;

    // http://localhost:8201/admin/acl/index/login
    // 1 login登录
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody PasswordLoginVo loginVo) {
        // 返回token值
        // 操作admin表
        Admin admin = adminService.getAdminByUserName(loginVo.getUsername());
        if (admin == null || !Objects.equals(admin.getPassword(), MD5.encrypt(loginVo.getPassword()))) {
            return Result.fail(null);
        }
        admin.setRoleCodes(StpUtil.getRoleList());
        admin.setPermissionCodes(StpUtil.getPermissionList());
        StpUtil.login(admin.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", "token-admin");
        map.put("user", admin);
        return Result.ok(map);
    }

    @SaCheckLogin
    @ApiOperation("获取角色")
    @PostMapping("/roles")
    public Result getRoleList() {
        return Result.ok(StpUtil.getRoleList());
    }

    @SaCheckLogin
    @ApiOperation("获取权限")
    @PostMapping("/permissions")
    public Result getPermissionList() {
        return Result.ok(StpUtil.getPermissionList());
    }

    //    url: '/admin/acl/index/info',
//    method: 'get',
    //2 getInfo 获取信息
    @ApiOperation("获取信息")
    @GetMapping("info")
    public Result info() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "admin");
        map.put("avatar", "https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        return Result.ok(map);
    }

    //    url: '/admin/acl/index/logout',
//    method: 'post'
    //3 logout 退出
    @ApiOperation("退出")
    @PostMapping("logout")
    public Result logout() {
        return Result.ok(null);
    }
}
