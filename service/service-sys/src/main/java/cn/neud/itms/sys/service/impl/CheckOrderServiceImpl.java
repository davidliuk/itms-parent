package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.CheckOrder;
import cn.neud.itms.sys.mapper.CheckOrderMapper;
import cn.neud.itms.sys.service.CheckOrderService;
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
public class CheckOrderServiceImpl extends ServiceImpl<CheckOrderMapper, CheckOrder> implements CheckOrderService {

    @Override
    public IPage<CheckOrder> selectPage(Page<CheckOrder> pageParam, CheckOrder checkOrder) {
        Long skuId = checkOrder.getSkuId();
        Long id = checkOrder.getId();
        Long wareId = checkOrder.getWareId();
        Long stationId = checkOrder.getStationId();
        Integer status = checkOrder.getStatus();
        String skuName = checkOrder.getSkuName();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<CheckOrder>()
                .eq(!StringUtils.isEmpty(skuId), CheckOrder::getSkuId, skuId)
                .eq(!StringUtils.isEmpty(id), CheckOrder::getId, id)
                .eq(!StringUtils.isEmpty(wareId), CheckOrder::getWareId, wareId)
                .eq(!StringUtils.isEmpty(stationId), CheckOrder::getStationId, stationId)
                .eq(!StringUtils.isEmpty(status), CheckOrder::getStatus, status)
                .like(!StringUtils.isEmpty(skuName), CheckOrder::getSkuName, skuName));
    }
}
