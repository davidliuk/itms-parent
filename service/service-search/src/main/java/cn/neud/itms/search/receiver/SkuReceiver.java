package cn.neud.itms.search.receiver;

import cn.neud.itms.mq.constant.MqConstant;
import cn.neud.itms.search.service.SkuService;
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
public class SkuReceiver {

    @Autowired
    private SkuService skuService;

    //商品上架
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_GOODS_UPPER, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_GOODS_DIRECT),
            key = {MqConstant.ROUTING_GOODS_UPPER}
    ))
    public void upperSku(Long skuId, Message message, Channel channel) throws IOException {
        if (skuId != null) {
            //调用方法商品上架
            skuService.upperSku(skuId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

    //商品下架
    @RabbitListener(bindings = @QueueBinding(
            value = @Queue(value = MqConstant.QUEUE_GOODS_LOWER, durable = "true"),
            exchange = @Exchange(value = MqConstant.EXCHANGE_GOODS_DIRECT),
            key = {MqConstant.ROUTING_GOODS_LOWER}
    ))
    public void lowerSku(Long skuId, Message message, Channel channel) throws IOException {
        if (skuId != null) {
            skuService.lowerSku(skuId);
        }
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), false);
    }

}
