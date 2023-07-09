package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum OrderStatus {
    UNPAID(0, "待支付"), // 用户支付
    PAID(1, "已支付"), // 调度
    DISPATCH(2, "已调度"), // 区域中心库房出库
    OUT(3, "已出库"), // 分站库房出库
    IN(4, "已入库"),
//    WAITING_ASSIGN(5, "待分配"), // 应该和已入库合并
    ASSIGN(5, "已分配"),
//    WAITING_TAKE(7, "待领货"),
//    WAITING_COURIER_TAKE(7, "待领货"), // 应该和已分配合并
    TAKE(6, "已领货"), // 待收货
    RECEIVE(7 , "完成"), //
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    OrderStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}