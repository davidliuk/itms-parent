package cn.neud.itms.acl.service.impl;

import cn.neud.itms.acl.mapper.AdminMapper;
import cn.neud.itms.acl.service.AdminService;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.model.user.User;
import cn.neud.itms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {
    //1 用户列表
    @Override
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        LambdaQueryWrapper<Admin> wrapper = new LambdaQueryWrapper<>();
        if(!StringUtils.isEmpty(username)) {
            wrapper.eq(Admin::getUsername,username);
        }
        if(!StringUtils.isEmpty(name)) {
            wrapper.like(Admin::getName,name);
        }
        IPage<Admin> adminPage = baseMapper.selectPage(pageParam, wrapper);
        return adminPage;
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, username)
        );
    }
}
