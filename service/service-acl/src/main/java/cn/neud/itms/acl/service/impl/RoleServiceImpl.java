package cn.neud.itms.acl.service.impl;

import cn.neud.itms.acl.mapper.RoleMapper;
import cn.neud.itms.acl.service.AdminRoleService;
import cn.neud.itms.acl.service.RoleService;
import cn.neud.itms.model.acl.AdminRole;
import cn.neud.itms.model.acl.Role;
import cn.neud.itms.vo.acl.RoleQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    // 用户角色关系
    @Autowired
    private AdminRoleService adminRoleService;

    // 1 角色列表（条件分页查询）
    @Override
    public IPage<Role> selectRolePage(
            Page<Role> pageParam,
            RoleQueryVo roleQueryVo
    ) {
        //获取条件值
        Long id = roleQueryVo.getId();
        String name = roleQueryVo.getName();
        String remark = roleQueryVo.getRemark();
        String code = roleQueryVo.getCode();
        Date createTime = roleQueryVo.getCreateTime();
        Date updateTime = roleQueryVo.getUpdateTime();

        //创建mp条件对象
        //判断条件值是否为空，不为封装查询条件
        // rolename like ?
        //调用方法实现条件分页查询
        //返回分页对象
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Role>()
                .eq(!StringUtils.isEmpty(id), Role::getId, id)
                .like(!StringUtils.isEmpty(name), Role::getName, name)
                .like(!StringUtils.isEmpty(remark), Role::getRemark, remark)
                .like(!StringUtils.isEmpty(code), Role::getCode, code)
                .ge(!StringUtils.isEmpty(createTime), Role::getCreateTime, createTime)
                .ge(!StringUtils.isEmpty(updateTime), Role::getUpdateTime, updateTime)
        );
    }

    // 获取所有角色，和根据用户id查询用户分配角色列表
    @Override
    public Map<String, Object> getRoleByAdminId(Long adminId) {
        //1 查询所有角色
        List<Role> allRolesList = baseMapper.selectList(null);

        //2 根据用户id查询用户分配角色列表
        //2.1 根据用户id查询 用户角色关系表 admin_role 查询用户分配角色id列表
        List<AdminRole> adminRoleList = adminRoleService.list(new LambdaQueryWrapper<AdminRole>()
                .eq(AdminRole::getAdminId, adminId)
        );

        //2.2 通过第一步返回集合，获取所有角色id的列表List<AdminRole> -- List<Long>
        List<Long> roleIdsList = adminRoleList.stream()
                .map(AdminRole::getRoleId)
                .collect(Collectors.toList());

        //2.3 创建新的list集合，用于存储用户配置角色
        List<Role> assignRoleList = new ArrayList<>();

        //2.4 遍历所有角色列表 allRolesList，得到每个角色
        //判断所有角色里面是否包含已经分配角色id，封装到2.3里面新的list集合
        for (Role role : allRolesList) {
            //判断
            if (roleIdsList.contains(role.getId())) {
                assignRoleList.add(role);
            }
        }

        //封装到map，返回
        Map<String, Object> result = new HashMap<>();
        //所有角色列表
        result.put("allRolesList", allRolesList);
        //用户分配角色列表
        result.put("assignRoles", assignRoleList);
        return result;
    }

    // 为用户进行分配
    @Override
    public void saveAdminRole(Long adminId, Long[] roleIds) {
        // 1 删除用户已经分配过的角色数据
        adminRoleService.remove(new LambdaQueryWrapper<AdminRole>()
                .eq(AdminRole::getAdminId, adminId)
        );

        // 2 重新分配
        List<AdminRole> list = new ArrayList<>();
        for (Long roleId : roleIds) {
            AdminRole adminRole = new AdminRole();
            adminRole.setAdminId(adminId);
            adminRole.setRoleId(roleId);
            //放到list集合
            list.add(adminRole);
        }
        // 3 调用批处理方法添加
        adminRoleService.saveBatch(list);
    }
}
