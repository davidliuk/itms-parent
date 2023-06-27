package cn.neud.itms.mq.constant;

public class MqConstant {
    /**
     * 消息补偿
     */
    public static final String MQ_KEY_PREFIX = "itms.mq:list";
    public static final int RETRY_COUNT = 3;

    /**
     * 商品上下架
     */
    public static final String EXCHANGE_GOODS_DIRECT = "itms.goods.direct";
    public static final String ROUTING_GOODS_UPPER = "itms.goods.upper";
    public static final String ROUTING_GOODS_LOWER = "itms.goods.lower";
    //队列
    public static final String QUEUE_GOODS_UPPER = "itms.goods.upper";
    public static final String QUEUE_GOODS_LOWER = "itms.goods.lower";

    /**
     * 配送员上下线
     */
    public static final String EXCHANGE_COURIER_DIRECT = "itms.courier.direct";
    public static final String ROUTING_COURIER_UPPER = "itms.courier.upper";
    public static final String ROUTING_COURIER_LOWER = "itms.courier.lower";
    //队列
    public static final String QUEUE_COURIER_UPPER = "itms.courier.upper";
    public static final String QUEUE_COURIER_LOWER = "itms.courier.lower";

    //订单
    public static final String EXCHANGE_ORDER_DIRECT = "itms.order.direct";
    public static final String ROUTING_ROLLBACK_STOCK = "itms.rollback.stock";
    public static final String ROUTING_MINUS_STOCK = "itms.minus.stock";

    public static final String ROUTING_DELETE_CART = "itms.delete.cart";
    //解锁普通商品库存
    public static final String QUEUE_ROLLBACK_STOCK = "itms.rollback.stock";
    public static final String QUEUE_SECKILL_ROLLBACK_STOCK = "itms.seckill.rollback.stock";
    public static final String QUEUE_MINUS_STOCK = "itms.minus.stock";
    public static final String QUEUE_DELETE_CART = "itms.delete.cart";

    //支付
    public static final String EXCHANGE_PAY_DIRECT = "itms.pay.direct";
    public static final String ROUTING_PAY_SUCCESS = "itms.pay.success";
    public static final String QUEUE_ORDER_PAY = "itms.order.pay";
    public static final String QUEUE_COURIER_BILL = "itms.courier.bill";

    //取消订单
    public static final String EXCHANGE_CANCEL_ORDER_DIRECT = "itms.cancel.order.direct";
    public static final String ROUTING_CANCEL_ORDER = "itms.cancel.order";
    //延迟取消订单队列
    public static final String QUEUE_CANCEL_ORDER = "itms.cancel.order";

    /**
     * 定时任务
     */
    public static final String EXCHANGE_DIRECT_TASK = "itms.exchange.direct.task";
    public static final String ROUTING_TASK_23 = "itms.task.23";
    //队列
    public static final String QUEUE_TASK_23 = "itms.queue.task.23";
}