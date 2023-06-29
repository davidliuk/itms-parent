package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

@Getter
public enum StorageType {
    IN(0,"入库"),
    OUT(1,"出库" );

    @EnumValue
    private Integer code ;
    private String comment ;

    StorageType(Integer code, String comment ){
        this.code=code;
        this.comment=comment;
    }
}