package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.RegionWare;
import cn.neud.itms.model.sys.Ware;
import cn.neud.itms.model.sys.WorkOrder;
import cn.neud.itms.vo.sys.RegionWareQueryVo;
import cn.neud.itms.vo.sys.WorkOrderQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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

    IPage<WorkOrder> selectPage(Page<WorkOrder> pageParam, WorkOrderQueryVo workOrderQueryVo);

    void updateByOrderId(WorkOrder workOrder);
}
