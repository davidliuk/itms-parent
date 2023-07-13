package cn.neud.itms.acl.service.impl;

import cn.neud.itms.acl.mapper.AdminRoleMapper;
import cn.neud.itms.acl.mapper.RolePermissionMapper;
import cn.neud.itms.acl.service.AdminRoleService;
import cn.neud.itms.acl.service.RolePermissionService;
import cn.neud.itms.model.acl.AdminRole;
import cn.neud.itms.model.acl.RolePermission;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class RolePermissionServiceImpl extends ServiceImpl<RolePermissionMapper, RolePermission> implements RolePermissionService {
}
