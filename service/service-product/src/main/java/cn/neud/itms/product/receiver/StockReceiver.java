package cn.neud.itms.product.receiver;

import cn.neud.itms.model.order.OrderInfo;
import cn.neud.itms.mq.constant.MqConstant;
import cn.neud.itms.order.client.OrderFeignClient;
import cn.neud.itms.product.service.SkuInfoService;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;

@Component
public class StockReceiver {

    @Autowired
    private SkuInfoService skuInfoService;

    @Autowired
    private OrderFeignClient orderFeignClient;

    /**
     * 扣减库存成功，更新订单状态
     *
     * @param orderNo
     * @throws IOException
     */
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_MINUS_STOCK, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_ORDER_DIRECT),
            key = {MqConstant.ROUTING_MINUS_STOCK}
    ))
    public void minusStock(
            String orderNo,
            Message message,
            Channel channel
    ) throws IOException {
        if (!StringUtils.isEmpty(orderNo)) {
            OrderInfo order = orderFeignClient.getOrderDetailByNo(orderNo);
            skuInfoService.minusStock(order.getWareId(), orderNo);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
