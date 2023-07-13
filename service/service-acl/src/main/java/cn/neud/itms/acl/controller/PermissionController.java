package cn.neud.itms.acl.controller;

import cn.dev33.satoken.annotation.SaCheckPermission;
import cn.dev33.satoken.annotation.SaCheckRole;
import cn.neud.itms.acl.service.PermissionService;
import cn.neud.itms.common.auth.RoleConstant;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin/acl/permission")
@Api(tags = "菜单管理")
//@CrossOrigin //跨域
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 参数有用户id 和 多个角色id
    @ApiOperation("为用户进行角色分配")
    @PostMapping("doAssign/{roleId}")
    public Result doAssign(
            @PathVariable Long roleId,
            @RequestBody Long[] permissionId
    ) {
        permissionService.saveRolePermission(roleId, permissionId);
        return Result.ok(null);
    }

    // 获取所有角色，和根据用户id查询用户分配角色列表
    @ApiOperation("获取用户角色")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        Map<String, Object> map = permissionService.getPermissionByRoleId(roleId);
        return Result.ok(map);
    }

    //查询所有菜单
//    url: `${api_name}`,
//    method: 'get'
    @ApiOperation("查询所有菜单")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //添加菜单
//    url: `${api_name}/save`,
//    method: "post",
//    data: permission
    @ApiOperation("添加菜单")
    @PostMapping("")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    //修改菜单
//    url: `${api_name}/update`,
//    method: "put",
//    data: permission
    @ApiOperation("修改菜单")
    @PutMapping("")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }

    //递归删除菜单
//    url: `${api_name}/remove/${id}`,
//    method: "delete"
    @ApiOperation("递归删除菜单")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }
}
