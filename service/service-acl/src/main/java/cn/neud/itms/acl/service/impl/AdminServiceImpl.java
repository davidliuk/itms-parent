package cn.neud.itms.acl.service.impl;

import cn.neud.itms.acl.mapper.AdminMapper;
import cn.neud.itms.acl.mapper.AdminRoleMapper;
import cn.neud.itms.acl.service.AdminService;
import cn.neud.itms.model.acl.Admin;
import cn.neud.itms.model.acl.AdminRole;
import cn.neud.itms.vo.acl.AdminQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AdminServiceImpl extends ServiceImpl<AdminMapper, Admin> implements AdminService {

    @Autowired
    private AdminRoleMapper adminRoleMapper;

    //1 用户列表
    @Override
    public IPage<Admin> selectPageUser(Page<Admin> pageParam, AdminQueryVo adminQueryVo) {
        Long id = adminQueryVo.getId();
        Long wareId = adminQueryVo.getWareId();
        Long stationId = adminQueryVo.getStationId();
        String username = adminQueryVo.getUsername();
        String name = adminQueryVo.getName();
        String phone = adminQueryVo.getPhone();
        String email = adminQueryVo.getEmail();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Admin>()
                .eq(!StringUtils.isEmpty(id), Admin::getId, id)
                .eq(!StringUtils.isEmpty(wareId), Admin::getWareId, wareId)
                .eq(!StringUtils.isEmpty(stationId), Admin::getStationId, stationId)
                .like(!StringUtils.isEmpty(username), Admin::getUsername, username)
                .like(!StringUtils.isEmpty(name), Admin::getName, name)
                .like(!StringUtils.isEmpty(phone), Admin::getPhone, phone)
                .like(!StringUtils.isEmpty(email), Admin::getEmail, email));
    }

    @Override
    public Admin getAdminByUserName(String username) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<Admin>().eq(Admin::getUsername, username)
        );
    }

    @Override
    public Admin getAdminByEmail(String email) {
        return baseMapper.selectOne(
                new LambdaQueryWrapper<Admin>().eq(Admin::getEmail, email)
        );
    }

    @Override
    public List<Admin> getByRoleId(Long id) {
        List<Long> ids = adminRoleMapper.selectList(new LambdaQueryWrapper<AdminRole>()
                        .eq(AdminRole::getRoleId, id)).stream()
                .map(AdminRole::getAdminId)
                .collect(Collectors.toList());
        return baseMapper.selectBatchIds(ids);
//        return baseMapper.selectByRoleId(id);

    }
}
