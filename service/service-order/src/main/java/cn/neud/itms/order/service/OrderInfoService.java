package cn.neud.itms.order.service;

import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.vo.order.OrderConfirmVo;
import cn.neud.itms.vo.order.OrderInfoQueryVo;
import cn.neud.itms.vo.order.OrderSubmitVo;
import cn.neud.itms.vo.order.OrderUserQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author david
 * @since 2023-04-18
 */
public interface OrderInfoService extends IService<OrderInfo> {

    //确认订单
    OrderConfirmVo confirmOrder();

    //生成订单
    Long submitOrder(OrderSubmitVo orderParamVo);

    //订单详情
    OrderInfo getOrderInfoById(Long orderId);

    //根据orderNo查询订单信息
    OrderInfo getOrderInfoByOrderNo(String orderNo);

    // 根据orderNo查询订单信息
    OrderInfo getOrderInfoByOrderId(Long id);

    OrderInfo getOrderDetailByOrderNo(String orderNo);

    //订单支付成功，更新订单状态，扣减库存
    void orderPay(String orderNo);

    //订单查询
    IPage<OrderInfo> getOrderInfoByUserIdPage(Page<OrderInfo> pageParam, OrderUserQueryVo orderUserQueryVo);

    IPage<OrderInfo> selectPage(Page<OrderInfo> pageParam, OrderInfoQueryVo orderInfoQueryVo);

    OrderInfo getOrderInfoDetail(Long orderId);

    void returnOrder(String orderNo);
}
