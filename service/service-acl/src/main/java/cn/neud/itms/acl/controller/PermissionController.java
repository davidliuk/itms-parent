package cn.neud.itms.acl.controller;

import cn.neud.itms.acl.service.PermissionService;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.acl.Permission;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "权限管理")
@RestController
@RequestMapping("/admin/acl/permission")
public class PermissionController {

    @Autowired
    private PermissionService permissionService;

    // 查询所有权限
//    url: `${api_name}`,
//    method: 'get'
    @ApiOperation("查询所有权限")
    @GetMapping
    public Result list() {
        List<Permission> list = permissionService.queryAllPermission();
        return Result.ok(list);
    }

    //递归删除权限
//    url: `${api_name}/remove/${id}`,
//    method: "delete"
    @ApiOperation("递归删除权限")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        permissionService.removeChildById(id);
        return Result.ok(null);
    }

    // 添加权限
//    url: `${api_name}/save`,
//    method: "post",
//    data: permission
    @ApiOperation("添加权限")
    @PostMapping("")
    public Result save(@RequestBody Permission permission) {
        permissionService.save(permission);
        return Result.ok(null);
    }

    //修改权限
//    url: `${api_name}/update`,
//    method: "put",
//    data: permission
    @ApiOperation("修改权限")
    @PutMapping("")
    public Result update(@RequestBody Permission permission) {
        permissionService.updateById(permission);
        return Result.ok(null);
    }
}
