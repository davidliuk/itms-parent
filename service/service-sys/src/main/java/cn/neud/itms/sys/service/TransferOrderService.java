package cn.neud.itms.sys.service;

import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.TransferOrder;
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
public interface TransferOrderService extends IService<TransferOrder> {

    IPage<TransferOrder> selectPage(Page<TransferOrder> pageParam, TransferOrder workOrder);

    void updateByOrderId(TransferOrder workOrder, WorkType type);

    TransferOrder getByOrderId(Long orderId, WorkType type);
}
