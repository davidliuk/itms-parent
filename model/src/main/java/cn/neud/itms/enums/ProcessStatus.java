package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ProcessStatus {
    UNPAID(1, OrderStatus.UNPAID),
    WAITING_DISPATCH(2, OrderStatus.PAID),
    WAITING_COURIER_TAKE(3, OrderStatus.TAKE),
    WAITING_USER_TAKE(4, OrderStatus.TAKE),
    //    WAITING_COMMENT(5, OrderStatus.WAITING_COMMENT),
    FINISHED(6, OrderStatus.RECEIVE),
    CANCEL(-1, OrderStatus.CANCEL),
    PAY_FAIL(-2, OrderStatus.UNPAID);

    @EnumValue
    private Integer code;
    private OrderStatus orderStatus;

    ProcessStatus(Integer code, OrderStatus orderStatus) {
        this.code = code;
        this.orderStatus = orderStatus;
    }

}
