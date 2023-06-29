package cn.neud.itms.acl.service.impl;

import cn.neud.itms.acl.mapper.AdminRoleMapper;
import cn.neud.itms.acl.service.AdminRoleService;
import cn.neud.itms.model.acl.AdminRole;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

@Service
public class AdminRoleServiceImpl extends ServiceImpl<AdminRoleMapper, AdminRole> implements AdminRoleService {
}
