package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.sys.mapper.LogisticsMapper;
import cn.neud.itms.sys.service.LogisticsService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Service
public class LogisticsServiceImpl extends ServiceImpl<LogisticsMapper, Logistics> implements LogisticsService {

    @Override
    public IPage<Logistics> selectPage(Page<Logistics> pageParam, Logistics logistics) {
        Long id = logistics.getId();
        Long wareId = logistics.getWareId();
        String name = logistics.getName();
        String phone = logistics.getPhone();
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Logistics>()
                .eq(!StringUtils.isEmpty(id), Logistics::getId, id)
                .eq(!StringUtils.isEmpty(wareId), Logistics::getWareId, wareId)
                .like(!StringUtils.isEmpty(name), Logistics::getName, name)
                .like(!StringUtils.isEmpty(phone), Logistics::getPhone, phone)
        );
    }
}
