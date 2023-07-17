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
// 保证此类被 SpringBoot 扫描，完成 Sa-Token 的自定义权限验证扩展
@Component
public class StpInterfaceImpl implements StpInterface {

    @Autowired
    private RoleMapper roleMapper;

    @Autowired
    private PermissionService permissionService;

    @Autowired
    private RolePermissionMapper rolePermissionMapper;

    @Autowired
    private AdminRoleService adminRoleService;

    /**
     * 返回一个账号所拥有的权限码集合
     */
    @Override
    public List<String> getPermissionList(Object adminId, String loginType) {
        // 1.1 获取所有该用户id的AdminRole集合
        List<AdminRole> adminRoleList = adminRoleService.list(
                new LambdaQueryWrapper<AdminRole>()
                        .eq(AdminRole::getAdminId, adminId)
        );

        // 1.2 通过第一步返回集合，获取所有角色id的列表List<AdminRole> - List<Long>
        List<Long> roleIds = adminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        // 2.1 通过第二步返回的角色id列表，获取所有角色id对应的权限id列表
        List<Long> permissionIds = rolePermissionMapper.selectList(
                        new LambdaQueryWrapper<RolePermission>()
                                .in(RolePermission::getRoleId, roleIds)
                ).stream()
                .map(RolePermission::getPermissionId)
                .collect(Collectors.toList());
        if (permissionIds.size() == 0) {
            return new ArrayList<>();
        }
        // 2.2 通过第三步返回的权限id列表，获取所有权限id对应的权限列表List<Permission>
        List<Permission> permissions = permissionService.list(
                new LambdaQueryWrapper<Permission>()
                        .in(Permission::getId, permissionIds)
        );
        // 2.3 通过第四步返回的权限列表，获取所有权限code的列表
        return permissions.stream()
                .map(Permission::getCode)
                .collect(Collectors.toList());
    }

    /**
     * 返回一个账号所拥有的角色标识集合 (权限与角色可分开校验)
     */
    @Override
    public List<String> getRoleList(Object adminId, String loginType) {
        // 1.1 获取所有该用户id的AdminRole集合
        List<AdminRole> adminRoleList = adminRoleService.list(
                new LambdaQueryWrapper<AdminRole>()
                        .eq(AdminRole::getAdminId, adminId)
        );

        // 1.2 通过第一步返回集合，获取所有角色id的列表List<AdminRole> -- List<Long>
        List<Long> roleIds = adminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());
        if (roleIds.size() == 0) {
            return new ArrayList<>();
        }
        // 1.3 通过第二步返回集合，获取所有角色的列表
        List<Role> roles = roleMapper.selectList(
                new LambdaQueryWrapper<Role>()
                        .in(Role::getId, roleIds)
        );
        // 1.4 通过第三步返回集合，获取所有角色code的列表
        return roles.stream()
                .map(Role::getCode)
                .collect(Collectors.toList());
    }

}
