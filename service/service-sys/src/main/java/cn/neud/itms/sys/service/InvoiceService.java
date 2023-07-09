package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.Invoice;
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
public interface InvoiceService extends IService<Invoice> {

    IPage<Invoice> selectPage(Page<Invoice> pageParam, Invoice workOrderQueryVo);


    Invoice getByOrderId(Long orderId);
}
