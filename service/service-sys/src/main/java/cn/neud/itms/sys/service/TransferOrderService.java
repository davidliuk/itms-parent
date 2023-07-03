package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.TransferOrder;
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
public interface TransferOrderService extends IService<TransferOrder> {

    IPage<TransferOrder> selectPage(Page<TransferOrder> pageParam, TransferOrder workOrder);
//
//    void updateByOrderId(TransferOrder workOrder);
}
