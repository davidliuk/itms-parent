package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum PurchaseStatus {
    PAID(1, "已支付"), // 区域中心库房出库
    IN(4, "已入库"),
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    PurchaseStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}