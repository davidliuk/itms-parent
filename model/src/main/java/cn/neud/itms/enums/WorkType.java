package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

//@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
@Getter
public enum WorkType {
    DELIVERY(0, "送货"),
    EXCHANGE(1, "换货"),
    RETURN(2, "退货");

    @EnumValue
    private Integer code;
    private String comment;

    WorkType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}