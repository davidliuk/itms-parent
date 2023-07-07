package cn.neud.itms.enums;

import com.alibaba.fastjson.annotation.JSONType;
import com.baomidou.mybatisplus.annotation.EnumValue;
import com.fasterxml.jackson.databind.deser.std.EnumDeserializer;
import com.fasterxml.jackson.databind.ser.std.EnumSerializer;
import lombok.Getter;

@JSONType(serializer = EnumSerializer.class, deserializer = EnumDeserializer.class, serializeEnumAsJavaBean = true)
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