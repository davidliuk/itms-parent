package cn.neud.itms.order.receiver;

import cn.neud.itms.mq.constant.MqConstant;
import cn.neud.itms.order.service.OrderInfoService;
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
public class OrderReceiver {

    @Autowired
    private OrderInfoService orderInfoService;

    //订单支付成功，更新订单状态，扣减库存
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_ORDER_PAY, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_PAY_DIRECT),
            key = {MqConstant.ROUTING_PAY_SUCCESS}
    ))
    public void orderPay(String orderNo,
                         Message message,
                         Channel channel) throws IOException {
        if (!StringUtils.isEmpty(orderNo)) {
            orderInfoService.orderPay(orderNo);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
