package cn.neud.itms.sys.service;

import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
public interface WorkOrderService extends IService<WorkOrder> {

    IPage<WorkOrder> selectPage(Page<WorkOrder> pageParam, WorkOrderQueryVo workOrderQueryVo);

    void updateByOrderId(WorkOrder workOrder, WorkType type);

    WorkOrder getByOrderId(Long orderId, WorkType type);
}
