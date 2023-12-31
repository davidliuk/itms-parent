package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum InvoiceStatus {
    UNUSED(1, "未领用"),
    USED(2, "已领用"),
    CANCEL(-1, "已作废");

    @EnumValue
    private Integer code;
    private String comment;

    InvoiceStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}