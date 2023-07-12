package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.Receipt;
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
public interface ReceiptService extends IService<Receipt> {

    IPage<Receipt> selectPage(Page<Receipt> pageParam, Receipt receipt);

    Receipt getByOrderId(Long orderId);
}
