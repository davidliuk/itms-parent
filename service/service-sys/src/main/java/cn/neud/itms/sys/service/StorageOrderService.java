package cn.neud.itms.sys.service;

import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.vo.sys.StorageOrderQueryVo;
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
public interface StorageOrderService extends IService<StorageOrder> {

    IPage<StorageOrder> selectPage(Page<StorageOrder> pageParam, StorageOrderQueryVo workOrderQueryVo);

    int updateByOrderId(StorageOrder workOrder);

}
