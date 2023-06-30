package cn.neud.itms.enums;

import com.baomidou.mybatisplus.annotation.EnumValue;
import lombok.Getter;

//@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
@Getter
public enum OrderType {
    COMMON(0, "常规"),
    EXCHANGE(1, "换货"),
    RETURN(2, "退货"),
    CANCEL(3, "退订");

    @EnumValue
    private Integer code;
    private String comment;

    OrderType(Integer code, String comment) {
        this.code = code;
        this.comment = comment;
    }
}