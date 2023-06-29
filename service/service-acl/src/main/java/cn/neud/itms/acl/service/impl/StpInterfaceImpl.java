package cn.neud.itms.acl.service.impl;

import cn.dev33.satoken.stp.StpInterface;
import cn.neud.itms.acl.mapper.RoleMapper;
import cn.neud.itms.acl.mapper.RolePermissionMapper;
import cn.neud.itms.acl.service.AdminRoleService;
import cn.neud.itms.acl.service.PermissionService;
import cn.neud.itms.model.acl.AdminRole;
import cn.neud.itms.model.acl.Permission;
import cn.neud.itms.model.acl.Role;
import cn.neud.itms.model.acl.RolePermission;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 自定义权限加载接口实现类
 */
@Component    // 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    //用户角色关系
    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object adminId, String loginType) {
//        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询权限
//        List<String> list = new ArrayList<String>();
//        list.add("101");
//        list.add("user.add");
//        list.add("user.update");
//        list.add("user.get");
//        // list.add("user.delete");
//        list.add("art.*");
        List<AdminRole> adminRoleList = adminRoleService.list(
                new LambdaQueryWrapper<AdminRole>()
                        .eq(AdminRole::getAdminId, adminId));

        //2.2 通过第一步返回集合，获取所有角色id的列表List<AdminRole> -- List<Long>
        List<Long> roleIds = adminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        System.out.println("roleIds");
        System.out.println(roleIds);
        List<Long> permissionIds = rolePermissionMapper.selectList(
                        new LambdaQueryWrapper<RolePermission>()
                                .in(RolePermission::getRoleId, roleIds)).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        if (permissionIds.size() == 0) {
            return new ArrayList<>();
        }
        List<Permission> permissions = permissionService.list(
                new LambdaQueryWrapper<Permission>()
                        .in(Permission::getId, permissionIds));

        return permissions.stream().map(Permission::getCode).collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object adminId, String loginType) {
//        // 本 list 仅做模拟，实际项目中要根据具体业务逻辑来查询角色
//        List<String> list = new ArrayList<String>();
//        list.add("admin");
//        list.add("super-admin");
        System.out.println(loginType);
        List<AdminRole> adminRoleList = adminRoleService.list(
                new LambdaQueryWrapper<AdminRole>()
                        .eq(AdminRole::getAdminId, adminId));

        //2.2 通过第一步返回集合，获取所有角色id的列表List<AdminRole> -- List<Long>
        List<Long> roleIds = adminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        List<Role> roles = roleMapper.selectList(
                new LambdaQueryWrapper<Role>()
                        .in(Role::getId, roleIds));
        return roles.stream().map(Role::getCode).collect(Collectors.toList());
    }

}
