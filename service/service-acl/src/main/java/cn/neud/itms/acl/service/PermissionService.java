package cn.neud.itms.acl.service;

import cn.neud.itms.model.acl.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

public interface PermissionService extends IService<Permission> {

    //查询所有菜单
    List<Permission> queryAllPermission();

    //递归删除菜单
    void removeChildById(Long id);

    void saveRolePermission(Long roleId, Long[] permissionId);

    Map<String, Object> getPermissionByRoleId(Long roleId);
}
