package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.sys.mapper.LogisticsMapper;
import cn.neud.itms.sys.mapper.PurchaseOrderMapper;
import cn.neud.itms.sys.service.LogisticsService;
import cn.neud.itms.sys.service.PurchaseOrderService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 仓库表 服务实现类
 * </p>
 *
 * @author neud
 * @since 2023-04-03
 */
@Service
public class PurchaseOrderServiceImpl extends ServiceImpl<PurchaseOrderMapper, PurchaseOrder> implements PurchaseOrderService {

}
