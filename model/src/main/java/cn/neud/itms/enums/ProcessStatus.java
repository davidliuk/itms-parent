package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum ProcessStatus {
    UNPAID(1, OrderStatus.UNPAID),
    WAITING_DISPATCH(2, OrderStatus.WAITING_DISPATCH),
    WAITING_COURIER_TAKE(3, OrderStatus.WAITING_TAKE),
    WAITING_USER_TAKE(4, OrderStatus.WAITING_TAKE),
    //    WAITING_COMMENT(5, OrderStatus.WAITING_COMMENT),
    FINISHED(6, OrderStatus.FINISHED),
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
