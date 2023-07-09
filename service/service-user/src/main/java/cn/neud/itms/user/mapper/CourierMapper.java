package cn.neud.itms.user.mapper;

import cn.neud.itms.model.user.CourierInfo;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CourierMapper extends BaseMapper<CourierInfo> {

    Integer addWorkNum(@Param("courierId") Long courierId, @Param("workNum") Integer workNum);

}
