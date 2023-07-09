package cn.neud.itms.sys.service.impl;

import cn.neud.itms.model.sys.Receipt;
import cn.neud.itms.sys.mapper.ReceiptMapper;
import cn.neud.itms.sys.service.ReceiptService;
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
 * @author neud
 * @since 2023-04-03
 */
@Service
public class ReceiptServiceImpl extends ServiceImpl<ReceiptMapper, Receipt> implements ReceiptService {

    // 开通区域列表
    @Override
    public IPage<Receipt> selectPage(
            Page<Receipt> pageParam,
            Receipt receipt
    ) {
        //1 获取查询条件值
        Long id = receipt.getId();
        Long stationId = receipt.getStationId();
        Long userId = receipt.getUserId();
        Long orderId = receipt.getOrderId();
        String userName = receipt.getUserName();
        String remark = receipt.getRemark();
        String stationName = receipt.getStationName();
        Integer mark = receipt.getMark();
        Boolean takeInvoice = receipt.getTakeInvoice();

        //2 判断条件值是否为空，不为空封装条件
        //3 调用方法实现条件分页查询
        //4 数据返回
        return baseMapper.selectPage(pageParam, new LambdaQueryWrapper<Receipt>()
                .eq(!StringUtils.isEmpty(id), Receipt::getId, id)
                .eq(!StringUtils.isEmpty(stationId), Receipt::getStationId, stationId)
                .eq(!StringUtils.isEmpty(userId), Receipt::getUserId, userId)
                .eq(!StringUtils.isEmpty(orderId), Receipt::getOrderId, orderId)
                .like(!StringUtils.isEmpty(userName), Receipt::getUserName, userName)
                .like(!StringUtils.isEmpty(remark), Receipt::getRemark, remark)
                .like(!StringUtils.isEmpty(stationName), Receipt::getStationName, stationName)
                .eq(!StringUtils.isEmpty(mark), Receipt::getMark, mark)
                .eq(!StringUtils.isEmpty(takeInvoice), Receipt::getTakeInvoice, takeInvoice)
        );
    }

    @Override
    public Receipt getByOrderId(Long orderId) {
        return baseMapper.selectOne(new LambdaQueryWrapper<Receipt>()
                .eq(Receipt::getOrderId, orderId)
        );
    }

}
