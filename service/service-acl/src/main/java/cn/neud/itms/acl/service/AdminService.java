package cn.neud.itms.acl.service;

import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

public interface AdminService extends IService<Admin> {

    //1 用户列表
    IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo);

    Admin getAdminByUserName(String username);
}
