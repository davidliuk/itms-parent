package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    //订单状态【0->待付款；1->待发货；2->待配送员收货；3->待用户收货，已完成；-1->已取消】
    UNPAID(0, "待支付"),
    WAITING_DISPATCH(1, "可调度"),
    DISPATCH(2, "已调度"),
    OUT(3, "已出库"),
    IN(4, "已入库"),
    WAITING_ASSIGN(5, "待分配"),
    ASSIGN(6, "已分配"),
    WAITING_TAKE(7, "待领货"),
    FINISHED(8, "完成"),
    CANCEL(-1, "取消");
//    WAITING_DELIVERY(1, "待发货"),
//    WAITING_TAKE(2, "待提货"),
//    WAITING_COMMENT(3, "待评论"),
//    FINISHED(4, "已完结"),
//    CANCEL(-1, "已取消");

    @EnumValue
    private Integer code;
    private String comment;

    OrderStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}