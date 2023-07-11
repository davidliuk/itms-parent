package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum WorkStatus {
    //订单状态【0->待付款；1->待发货；2->待配送员收货；3->待用户收货，已完成；-1->已取消】
//    DISPATCH(0, "已调度"),
//    OUT(1, "已出库"),
//    IN(2, "已入库"),
//    //    WAITING_ASSIGN(3, "待分配"), // 应该和已入库合并
//    ASSIGN(3, "已分配"),
//    //    WAITING_COURIER_TAKE(5, "待领货"),
//    TAKE(4, "待收货"),
//    RECEIVE(5, "完成"),
    DISPATCH(2, "已调度"), // 区域中心库房出库
    OUT(3, "已出库"), // 分站库房出库
    IN(4, "已入库"),
    //    WAITING_ASSIGN(5, "待分配"), // 应该和已入库合并
    ASSIGN(5, "已分配"),
    //    WAITING_TAKE(7, "待领货"),
//    WAITING_COURIER_TAKE(7, "待领货"), // 应该和已分配合并
    TAKE(6, "已领货"), // 待收货
    RECEIVE(7, "完成"), //
    RETURN_UNASSIGNED(8, "退货未分配"), //
    RETURN_ASSIGN(9, "退货分配"), //
    RETURN_TAKE(10, "退货领货"), //
    RETURN_STATION(11, "退货入站"), //
    RETURN_OUT(12, "货物出库"), //
    RETURN_IN(13, "货物入库"), //
//    RETURN(9, "货物退回"), //
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    WorkStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}