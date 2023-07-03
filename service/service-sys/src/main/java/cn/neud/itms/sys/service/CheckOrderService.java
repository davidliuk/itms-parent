package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.CheckOrder;
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
public interface CheckOrderService extends IService<CheckOrder> {

    IPage<CheckOrder> selectPage(Page<CheckOrder> pageParam, CheckOrder checkOrder);
}
