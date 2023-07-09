package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum CheckStatus {
    OUT(3, "已出库"), // 分站库房出库
    IN(4, "已入库"),
    //    WAITING_ASSIGN(5, "待分配"), // 应该和已入库合并
    CANCEL(-1, "取消");
//    WAITING_DELIVERY(1, "待发货"),
//    WAITING_TAKE(2, "待提货"),
//    WAITING_COMMENT(3, "待评论"),
//    FINISHED(4, "已完结"),
//    CANCEL(-1, "已取消");

    @EnumValue
    private Integer code;
    private String comment;

    CheckStatus(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}
