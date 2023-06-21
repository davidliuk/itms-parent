package cn.neud.itms.common.exception;

import cn.neud.itms.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class ItmsException extends RuntimeException {

    //异常状态码
    private Integer code;

    /**
     * 通过状态码和错误消息创建异常对象
     *
     * @param message
     * @param code
     */
    public ItmsException(String message, Integer code) {
        super(message);
        this.code = code;
    }


    /**
     * 接收枚举类型对象
     *
     * @param resultCodeEnum
     */
    public ItmsException(ResultCodeEnum resultCodeEnum) {
        super(resultCodeEnum.getMessage());
        this.code = resultCodeEnum.getCode();
    }

}
