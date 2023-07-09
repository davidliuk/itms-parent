package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum TransferStatus {
    DISPATCH(2, "已调度"), // 区域中心库房出库
    OUT(3, "已出库"), // 分站库房出库
    IN(4, "已入库"),
    CANCEL(-1, "取消");

    @EnumValue
    private Integer code;
    private String comment;

    TransferStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}