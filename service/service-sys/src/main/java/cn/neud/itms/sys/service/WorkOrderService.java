package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.Ware;
import cn.neud.itms.model.sys.WorkOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 仓库表 服务类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
public interface WorkOrderService extends IService<WorkOrder> {

    void updateByOrderId(WorkOrder workOrder);
}