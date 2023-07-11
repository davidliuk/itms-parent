package cn.neud.itms.acl.controller;

import cn.dev33.satoken.annotation.SaCheckRole;
import cn.neud.itms.acl.service.AdminService;
import cn.neud.itms.acl.service.RoleService;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.utils.MD5;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "用户管理")
@RestController
@RequestMapping("/admin/acl/user")
//@CrossOrigin
public class AdminController {

    @Autowired
    private AdminService adminService;

    @Autowired
    private RoleService roleService;

    //参数有用户id 和 多个角色id
    @ApiOperation("为用户进行角色分配")
    @PostMapping("doAssign/{adminId}")
    @SaCheckRole(RoleConstant.SYSTEM)
    public Result doAssign(
            @PathVariable Long adminId,
            @RequestBody Long[] roleId
    ) {
        roleService.saveAdminRole(adminId, roleId);
        return Result.ok(null);
    }

    //获取所有角色，和根据用户id查询用户分配角色列表
    @ApiOperation("获取用户角色")
    @GetMapping("toAssign/{adminId}")
    public Result toAssign(@PathVariable Long adminId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        Map<String, Object> map = roleService.getRoleByAdminId(adminId);
        return Result.ok(map);
    }

    //1 用户列表
    @ApiOperation("用户列表")
    @PostMapping("/{current}/{limit}")
    public Result list(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody AdminQueryVo adminQueryVo
    ) {
        System.out.println(adminQueryVo.toString());
        Page<Admin> pageParam = new Page<Admin>(current, limit);
        IPage<Admin> pageModel = adminService.selectPageUser(pageParam, adminQueryVo);
        return Result.ok(pageModel);
    }

    //2 id查询用户
    @ApiOperation("根据id查询")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Admin admin = adminService.getById(id);
        return Result.ok(admin);
    }

    //3 添加用户
    @ApiOperation("添加用户")
    @PostMapping("")
    @SaCheckRole(RoleConstant.SYSTEM)
    public Result save(@RequestBody Admin admin) {
        //获取输入的密码
        String password = admin.getPassword();
        //对输入密码进行加密 MD5
        String passwordMD5 = MD5.encrypt(password);
        //设置到admin对象里面
        admin.setPassword(passwordMD5);
        //调用方法添加
        adminService.save(admin);
        return Result.ok(null);
    }

    //4 修改用户(不能修改密码)，传入的密码值为null的时候不会改密码
    @ApiOperation("修改用户")
    @PutMapping("")
    public Result update(@RequestBody Admin admin) {
        adminService.updateById(admin);
        return Result.ok(null);
    }

    //5 id删除
    @ApiOperation("根据id删除用户")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        adminService.removeById(id);
        return Result.ok(null);
    }

    //6 批量删除
    @ApiOperation("批量删除")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        System.out.println(idList.toString());
        adminService.removeByIds(idList);
        return Result.ok(null);
    }

}