package cn.neud.itms.acl.controller;

import cn.neud.itms.acl.service.PermissionService;
import cn.neud.itms.acl.service.RoleService;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.model.acl.Role;
import cn.neud.itms.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色接口")
@RestController
@RequestMapping("/admin/acl/role")
//@CrossOrigin //跨域
public class RoleController {

    //注入service
    @Autowired
    private RoleService roleService;

    @Autowired
    private PermissionService permissionService;

    // 参数有用户id 和 多个角色id
    @ApiOperation("为角色进行权限分配")
    @PostMapping("doAssign/{roleId}")
    public Result doAssign(
            @PathVariable Long roleId,
            @RequestBody Long[] permissionId
    ) {
        permissionService.saveRolePermission(roleId, permissionId);
        return Result.ok(null);
    }

    // 获取所有角色，和根据用户id查询用户分配角色列表
    @ApiOperation("获取角色权限")
    @GetMapping("toAssign/{roleId}")
    public Result toAssign(@PathVariable Long roleId) {
        //返回map集合包含两部分数据：所有角色 和 为用户分配角色列表
        Map<String, Object> map = permissionService.getPermissionByRoleId(roleId);
        return Result.ok(map);
    }

    //1 角色列表（条件分页查询）
    @ApiOperation("角色条件分页查询")
    @PostMapping("{current}/{limit}")
    public Result pageList(
            @PathVariable Long current,
            @PathVariable Long limit,
            @RequestBody RoleQueryVo roleQueryVo
    ) {
        Page<Role> pageParam = new Page<>(current, limit);
        System.out.println(roleQueryVo.toString());
        IPage<Role> pageModel = roleService.selectRolePage(pageParam, roleQueryVo);
        return Result.ok(pageModel);
    }

    @ApiOperation("查询所有角色分类")
    @GetMapping("findAllList")
    public Result findAllList() {
        List<Role> list = roleService.list();
        return Result.ok(list);
    }

    //2 根据id查询角色
    @ApiOperation("根据id查询角色")
    @GetMapping("/{id}")
    public Result get(@PathVariable Long id) {
        Role role = roleService.getById(id);
        return Result.ok(role);
    }

    //3 添加角色
    @ApiOperation("添加角色")
    @PostMapping("")
    public Result save(@RequestBody Role role) {
        boolean is_success = roleService.save(role);
        if (is_success) {
            return Result.ok(null);
        } else {
            return Result.fail(null);
        }
    }

    //4 修改角色
    @ApiOperation("修改角色")
    @PutMapping("")
    public Result update(@RequestBody Role role) {
        roleService.updateById(role);
        return Result.ok(null);
    }

    //5 根据id删除角色
    @ApiOperation("根据id删除角色")
    @DeleteMapping("/{id}")
    public Result remove(@PathVariable Long id) {
        roleService.removeById(id);
        return Result.ok(null);
    }

    //6 批量删除角色
    // json数组[1,2,3]  --- java的list集合
    @ApiOperation("批量删除角色")
    @DeleteMapping("")
    public Result batchRemove(@RequestBody List<Long> idList) {
        roleService.removeByIds(idList);
        return Result.ok(null);
    }

}
