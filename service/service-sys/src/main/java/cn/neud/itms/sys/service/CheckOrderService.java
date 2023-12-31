package cn.neud.itms.sys.service;

import cn.neud.itms.enums.WorkType;
import cn.neud.itms.model.sys.CheckOrder;
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
public interface CheckOrderService extends IService<CheckOrder> {

    IPage<CheckOrder> selectPage(Page<CheckOrder> pageParam, CheckOrder checkOrder);

    //    CheckOrder selectByOrderId(String orderId);
    CheckOrder selectByWorkOrderId(Long workOrderId);

    int updateByOrderId(CheckOrder checkOrder, WorkType type);

    CheckOrder getByOrderId(Long orderId, WorkType type);
}
