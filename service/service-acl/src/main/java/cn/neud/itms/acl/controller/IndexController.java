package cn.neud.itms.acl.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.dev33.satoken.stp.StpUtil;
import cn.neud.itms.acl.service.AdminService;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.common.utils.MailUtil;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.redis.constant.RedisConstant;
import cn.neud.itms.vo.user.EmailLoginVo;
import cn.neud.itms.vo.user.PasswordLoginVo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@Api(tags = "登录接口")
@RestController
@RequestMapping("/admin/acl/index")
//@CrossOrigin //跨域
public class IndexController {

    @Autowired
    AdminService adminService;

    @Autowired
    private RedisTemplate redisTemplate;

    // 1 login登录
    @ApiOperation("登录")
    @PostMapping("/login")
    public Result login(@RequestBody PasswordLoginVo loginVo) {
        // 返回token值
        // 操作admin表
        Admin admin = null;
        if (!StringUtils.isEmpty(loginVo.getUsername())) {
            admin = adminService.getAdminByUserName(loginVo.getUsername());
        } else {
            admin = adminService.getAdminByEmail(loginVo.getEmail());
        }
        if (admin == null || !Objects.equals(admin.getPassword(), MD5.encrypt(loginVo.getPassword()))) {
            return Result.fail(null);
        }
        admin.setRoles(StpUtil.getRoleList());
//        admin.setPermissions(StpUtil.getPermissionList());
        StpUtil.login(admin.getId());
        Map<String, Object> map = new HashMap<>();
        map.put("token", StpUtil.getTokenValue());
        map.put("user", admin);
        return Result.ok(map);
    }

    @ApiOperation(value = "邮件验证码发送")
    @PostMapping("email/send")
    public Result emailCode(@RequestBody EmailLoginVo loginVo) {
        String email = loginVo.getEmail();
        String code = MailUtil.sendMail(email);
        redisTemplate.opsForValue()
                .set(RedisConstant.LOGIN_CODE_KEY + email,
                        code,
                        RedisConstant.LOGIN_CODE_TTL,
                        TimeUnit.MINUTES);
        return Result.ok(null);
    }

    // 用户邮件登录
    @ApiOperation(value = "邮件验证码登录")
    @PostMapping("login/email")
    public Result emailLogin(@RequestBody EmailLoginVo loginVo) {
        // 操作admin表
        Admin admin = adminService.getAdminByEmail(loginVo.getEmail());
        if (!Objects.equals(redisTemplate.opsForValue().get(RedisConstant.LOGIN_CODE_KEY + loginVo.getEmail()), loginVo.getCode())) {
            return Result.fail(null);
        }
        // 初次登录用户注册账户
        if (admin == null) {
            admin = new Admin();
            admin.setEmail(loginVo.getEmail());
            admin.setName("邮箱用户" + loginVo.getEmail());
            admin.setAvatar("");
            adminService.save(admin);
        }

        admin.setRoles(StpUtil.getRoleList());
        StpUtil.login(admin.getId());
        //8 需要数据封装到map返回
        Map<String, Object> map = new HashMap<>();
        map.put("user", admin);
        map.put("token", StpUtil.getTokenValue());
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
