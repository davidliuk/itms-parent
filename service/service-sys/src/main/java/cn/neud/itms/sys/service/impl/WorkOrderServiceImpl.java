package cn.neud.itms.sys.service.impl;

import cn.neud.itms.enums.WorkStatus;
import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.sys.mapper.WorkOrderMapper;
import cn.neud.itms.sys.service.WorkOrderService;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
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
public class WorkOrderServiceImpl extends ServiceImpl<WorkOrderMapper, WorkOrder> implements WorkOrderService {

    @Override
    public IPage<WorkOrder> selectPage(Page<WorkOrder> pageParam, WorkOrderQueryVo workOrderQueryVo) {
        //1 获取查询条件值
        String name = workOrderQueryVo.getName();
        Long courierId = workOrderQueryVo.getCourierId();
        Long userId = workOrderQueryVo.getUserId();
        Long wareId = workOrderQueryVo.getWareId();
        Long stationId = workOrderQueryVo.getStationId();
        Long orderId = workOrderQueryVo.getOrderId();
        WorkStatus status = workOrderQueryVo.getWorkStatus();
        WorkType type = workOrderQueryVo.getWorkType();
        Date startTime = workOrderQueryVo.getStartTime();
        Date endTime = workOrderQueryVo.getEndTime();

        // 2 判断条件值是否为空，不为空封装条件
        // 封装条件
        // 3 调用方法实现条件分页查询
        // 4 数据返回
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<WorkOrder>()
                .like(!StringUtils.isEmpty(name), WorkOrder::getName, name)
                .eq(courierId != null, WorkOrder::getCourierId, courierId)
                .eq(userId != null, WorkOrder::getUserId, userId)
                .eq(wareId != null, WorkOrder::getWareId, wareId)
                .eq(stationId != null, WorkOrder::getStationId, stationId)
                .eq(orderId != null, WorkOrder::getOrderId, orderId)
                .eq(!StringUtils.isEmpty(status), WorkOrder::getWorkStatus, status)
                .eq(!StringUtils.isEmpty(type), WorkOrder::getWorkType, type)
                .ge(startTime != null, WorkOrder::getCreateTime, startTime)
                .le(endTime != null, WorkOrder::getCreateTime, endTime));
    }

    @Override
    public void updateByOrderId(WorkOrder workOrder, WorkType type) {
        baseMapper.update(workOrder, new LambdaQueryWrapper<WorkOrder>()
                .eq(WorkOrder::getOrderId, workOrder.getOrderId())
                .eq(WorkOrder::getWorkType, type)
        );
    }

    @Override
    public WorkOrder getByOrderId(Long orderId, WorkType type) {
        // 判断任务单是否存在
        return baseMapper.selectOne(new LambdaQueryWrapper<WorkOrder>()
                        .eq(WorkOrder::getOrderId, orderId)
//                .le(WorkOrder::getWorkStatus, status)
                        .eq(WorkOrder::getWorkType, type)
        );
    }
}
