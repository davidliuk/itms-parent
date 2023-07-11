package cn.neud.itms.acl.mapper;

import cn.neud.itms.model.acl.Admin;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface AdminMapper extends BaseMapper<Admin> {

    List<Admin> selectByRoleId(Long id);

}
