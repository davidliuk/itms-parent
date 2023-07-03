package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Logistics;
import cn.neud.itms.model.sys.PurchaseOrder;
import cn.neud.itms.sys.mapper.LogisticsMapper;
import cn.neud.itms.sys.mapper.PurchaseOrderMapper;
import cn.neud.itms.sys.service.LogisticsService;
import cn.neud.itms.sys.service.PurchaseOrderService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

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

    @Override
    public IPage<PurchaseOrder> selectPage(Page<PurchaseOrder> pageParam, PurchaseOrder purchaseOrder) {
        Long skuId = purchaseOrder.getSkuId();
        Long id = purchaseOrder.getId();
        Long wareId = purchaseOrder.getWareId();
        Long supplierId = purchaseOrder.getSupplierId();
        String skuName = purchaseOrder.getSkuName();
        String supplierName = purchaseOrder.getSupplierName();

        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<PurchaseOrder>()
                .eq(!StringUtils.isEmpty(skuId), PurchaseOrder::getSkuId, skuId)
                .eq(!StringUtils.isEmpty(id), PurchaseOrder::getId, id)
                .eq(!StringUtils.isEmpty(wareId), PurchaseOrder::getWareId, wareId)
                .eq(!StringUtils.isEmpty(supplierId), PurchaseOrder::getSupplierId, supplierId)
                .like(!StringUtils.isEmpty(skuName), PurchaseOrder::getSkuName, skuName)
                .like(!StringUtils.isEmpty(supplierName), PurchaseOrder::getSupplierName, supplierName));
    }
}
