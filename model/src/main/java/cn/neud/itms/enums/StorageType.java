package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum StorageType {
    IN(0, "送货入库"),
    OUT(1, "送货出库"),
    RETURN_IN(2, "退货入库"),
    RETURN_OUT(3, "退货出库"),
    RETURN_SUPPLIER(4, "退货供应商"),
    SUPPLIER(5, "进货供应商");

    @EnumValue
    private Integer code;
    private String comment;

    StorageType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}