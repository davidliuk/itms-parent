package cn.neud.itms.cart.receiver;

import cn.neud.itms.cart.service.CartInfoService;
import cn.neud.itms.mq.constant.MqConstant;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.Exchange;
import org.springframework.amqp.rabbit.annotation.Queue;
import org.springframework.amqp.rabbit.annotation.QueueBinding;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class CartReceiver {

    @Autowired
    private CartInfoService cartInfoService;

    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_DELETE_CART, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_ORDER_DIRECT),
            key = {MqConstant.ROUTING_DELETE_CART}
    ))
    public void deleteCart(Long userId, Message message, Channel channel) throws IOException {
        if (userId != null) {
            cartInfoService.deleteCartChecked(userId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }
}
