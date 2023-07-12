package cn.neud.itms.sys.service.impl;

import cn.neud.itms.enums.StorageType;
import cn.neud.itms.model.sys.StorageOrder;
import cn.neud.itms.sys.mapper.StorageOrderMapper;
import cn.neud.itms.sys.service.StorageOrderService;
import cn.neud.itms.vo.sys.StorageOrderQueryVo;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Service
public class StorageOrderServiceImpl extends ServiceImpl<StorageOrderMapper, StorageOrder> implements StorageOrderService {

    // 开通区域列表
    @Override
    public IPage<StorageOrder> selectPage(
            Page<StorageOrder> pageParam,
            StorageOrderQueryVo storageOrderQueryVo
    ) {
        //1 获取查询条件值
        Long id = storageOrderQueryVo.getId();
        Long orderId = storageOrderQueryVo.getOrderId();
        Long stationId = storageOrderQueryVo.getStationId();
        Long wareId = storageOrderQueryVo.getWareId();
        Long skuId = storageOrderQueryVo.getSkuId();
        Long supplierId = storageOrderQueryVo.getSupplierId();
        String supplierName = storageOrderQueryVo.getSupplierName();
        String skuName = storageOrderQueryVo.getSkuName();
        StorageType storageType = storageOrderQueryVo.getStorageType();

        //2 判断条件值是否为空，不为空封装条件
        //3 调用方法实现条件分页查询
        //4 数据返回
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<StorageOrder>()
                .eq(!StringUtils.isEmpty(id), StorageOrder::getId, id)
                .eq(!StringUtils.isEmpty(orderId), StorageOrder::getOrderId, orderId)
                .eq(!StringUtils.isEmpty(stationId), StorageOrder::getStationId, stationId)
                .eq(!StringUtils.isEmpty(wareId), StorageOrder::getWareId, wareId)
                .eq(!StringUtils.isEmpty(skuId), StorageOrder::getSkuId, skuId)
                .eq(!StringUtils.isEmpty(supplierId), StorageOrder::getSupplierId, supplierId)
                .like(!StringUtils.isEmpty(skuName), StorageOrder::getSkuName, skuName)
                .like(!StringUtils.isEmpty(supplierName), StorageOrder::getStationId, supplierName)
                .eq(!StringUtils.isEmpty(storageType), StorageOrder::getStorageType, storageType));
    }

    @Override
    public int updateByOrderId(StorageOrder workOrder) {
        return baseMapper.update(workOrder, new LambdaQueryWrapper<StorageOrder>()
                .eq(StorageOrder::getOrderId, workOrder.getOrderId())
        );
    }

}
