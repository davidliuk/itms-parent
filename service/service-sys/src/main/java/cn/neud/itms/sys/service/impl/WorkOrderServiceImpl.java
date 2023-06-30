package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Ware;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.sys.mapper.WareMapper;
import cn.neud.itms.sys.mapper.WorkOrderMapper;
import cn.neud.itms.sys.service.WareService;
import cn.neud.itms.sys.service.WorkOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Service
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Override
    public void updateByOrderId(WorkOrder workOrder) {
        baseMapper.update(workOrder, new LambdaQueryWrapper<WorkOrder>()
                .eq(WorkOrder::getOrderId, workOrder.getOrderId())
        );
    }
}
