package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WorkStatus {
    //订单状态【0->待付款；1->待发货；2->待配送员收货；3->待用户收货，已完成；-1->已取消】
    DISPATCH(0, "已调度"),
    OUT(1, "已出库"),
    IN(2, "已入库"),
    WAITING_ASSIGN(3, "待分配"), // 应该和已入库合并
    ASSIGN(4, "已分配"),
    WAITING_COURIER_TAKE(5, "待领货"),
    WAITING_USER_TAKE(6, "待收货"),
    FINISHED(7, "完成"),
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    WorkStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}