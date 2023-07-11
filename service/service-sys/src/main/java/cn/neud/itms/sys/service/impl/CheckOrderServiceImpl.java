package cn.neud.itms.sys.service.impl;

import cn.neud.itms.enums.CheckStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.CheckOrder;
import cn.neud.itms.sys.mapper.CheckOrderMapper;
import cn.neud.itms.sys.service.CheckOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Date;

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
        Long id = checkOrder.getId();
        Long wareId = checkOrder.getWareId();
        Long stationId = checkOrder.getStationId();
        CheckStatus status = checkOrder.getStatus();
        Date inTime = checkOrder.getInTime();
        Date outTime = checkOrder.getOutTime();
//        Long skuId = checkOrder.getSkuId();
//        String skuName = checkOrder.getSkuName();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<CheckOrder>()
//                .eq(!StringUtils.isEmpty(skuId), CheckOrder::getSkuId, skuId)
//                .like(!StringUtils.isEmpty(skuName), CheckOrder::getSkuName, skuName)
                .eq(!StringUtils.isEmpty(id), CheckOrder::getId, id)
                .eq(!StringUtils.isEmpty(wareId), CheckOrder::getWareId, wareId)
                .eq(!StringUtils.isEmpty(stationId), CheckOrder::getStationId, stationId)
                .eq(!StringUtils.isEmpty(status), CheckOrder::getStatus, status)
                .ge(!StringUtils.isEmpty(inTime), CheckOrder::getInTime, inTime)
                .ge(!StringUtils.isEmpty(outTime), CheckOrder::getOutTime, outTime));
    }

    @Override
    public CheckOrder selectByWorkOrderId(Long workOrderId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<CheckOrder>()
                .eq(CheckOrder::getWorkOrderId, workOrderId)
        );
    }

    @Override
    public int updateByOrderId(CheckOrder checkOrder, WorkType type) {
        return baseMapper.update(checkOrder, new LambdaQueryWrapper<CheckOrder>()
                .eq(CheckOrder::getOrderId, checkOrder.getOrderId())
                .eq(CheckOrder::getType, type)
        );
    }

    @Override
    public CheckOrder getByOrderId(Long orderId, WorkType type) {
        return baseMapper.selectOne(new LambdaQueryWrapper<CheckOrder>()
                        .eq(CheckOrder::getOrderId, orderId)
//                .le(CheckOrder::getStatus, type)
                        .eq(CheckOrder::getType, type)
        );
    }
}
