package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WorkStatus {
    //订单状态【0->待付款；1->待发货；2->待配送员收货；3->待用户收货，已完成；-1->已取消】
    DISPATCH(0, "已调度"),
    WAITING_ASSIGN(1, "待分配"),
    ASSIGN(2, "已分配"),
    WAITING_COURIER_TAKE(3, "待领货"),
    WAITING_USER_TAKE(4, "待收货"),
    FINISHED(5, "完成"),
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    WorkStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}