package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Invoice;
import cn.neud.itms.sys.mapper.InvoiceMapper;
import cn.neud.itms.sys.service.InvoiceService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;

/**
 * <p>
 * 城市仓库关联表 服务实现类
 * </p>
 *
 * @author david
 * @since 2023-06-10
 */
@Service
public class InvoiceServiceImpl extends ServiceImpl<InvoiceMapper, Invoice> implements InvoiceService {

    // 开通区域列表
    @Override
    public IPage<Invoice> selectPage(
            Page<Invoice> pageParam,
            Invoice invoice
    ) {
        //1 获取查询条件值
        Long id = invoice.getId();
        Long stationId = invoice.getStationId();
        Long userId = invoice.getUserId();
        Long courierId = invoice.getCourierId();
        Long orderId = invoice.getOrderId();
        String userName = invoice.getUserName();
        String courierName = invoice.getCourierName();
        BigDecimal totalAmount = invoice.getTotalAmount();

        //2 判断条件值是否为空，不为空封装条件
        //3 调用方法实现条件分页查询
        //4 数据返回
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Invoice>()
                .eq(!StringUtils.isEmpty(id), Invoice::getId, id)
                .eq(!StringUtils.isEmpty(stationId), Invoice::getStationId, stationId)
                .eq(!StringUtils.isEmpty(userId), Invoice::getUserId, userId)
                .eq(!StringUtils.isEmpty(courierId), Invoice::getCourierId, courierId)
                .eq(!StringUtils.isEmpty(orderId), Invoice::getOrderId, orderId)
                .like(!StringUtils.isEmpty(userName), Invoice::getUserName, userName)
                .like(!StringUtils.isEmpty(courierName), Invoice::getCourierName, courierName)
                .ge(!StringUtils.isEmpty(totalAmount), Invoice::getTotalAmount, totalAmount));
    }

    @Override
    public Invoice getByOrderId(Long orderId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Invoice>()
                .eq(Invoice::getOrderId, orderId)
        );
    }

    @Override
    public void updateByOrderId(Invoice invoice) {
        baseMapper.update(invoice, new LambdaQueryWrapper<Invoice>()
                .eq(Invoice::getOrderId, invoice.getOrderId())
        );
    }

}
