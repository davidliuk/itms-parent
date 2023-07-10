package cn.neud.itms.sys.service.impl;

import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.TransferOrder;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.sys.mapper.TransferOrderMapper;
import cn.neud.itms.sys.service.TransferOrderService;
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
public class TransferOrderServiceImpl extends ServiceImpl<TransferOrderMapper, TransferOrder> implements TransferOrderService {

    @Override
    public IPage<TransferOrder> selectPage(Page<TransferOrder> pageParam, TransferOrder workOrder) {
        Long id = workOrder.getId();
        Long orderId = workOrder.getOrderId();
        Long workOrderId = workOrder.getWorkOrderId();
        Long wareId = workOrder.getWareId();
        Long stationId = workOrder.getStationId();
        Long logisticsId = workOrder.getLogisticsId();
        String logisticsName = workOrder.getLogisticsName();
        String stationName = workOrder.getStationName();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<TransferOrder>()
                .eq(!StringUtils.isEmpty(id), TransferOrder::getId, id)
                .eq(!StringUtils.isEmpty(orderId), TransferOrder::getOrderId, orderId)
                .eq(!StringUtils.isEmpty(workOrderId), TransferOrder::getWorkOrderId, workOrderId)
                .eq(!StringUtils.isEmpty(wareId), TransferOrder::getWareId, wareId)
                .eq(!StringUtils.isEmpty(stationId), TransferOrder::getStationId, stationId)
                .eq(!StringUtils.isEmpty(logisticsId), TransferOrder::getLogisticsId, logisticsId)
                .like(!StringUtils.isEmpty(logisticsName), TransferOrder::getLogisticsName, logisticsName)
                .like(!StringUtils.isEmpty(stationName), TransferOrder::getStationName, stationName));
    }

    @Override
    public void updateByOrderId(TransferOrder workOrder) {
        baseMapper.update(workOrder, new LambdaQueryWrapper<TransferOrder>()
                .eq(TransferOrder::getOrderId, workOrder.getOrderId())
        );
    }

    @Override
    public TransferOrder getByOrderId(Long orderId, Integer status) {
        return baseMapper.selectOne(new LambdaQueryWrapper<TransferOrder>()
                .eq(TransferOrder::getOrderId, orderId)
                .le(TransferOrder::getStatus, status)
                .eq(TransferOrder::getType, WorkType.DELIVERY)
        );
    }
}
