package cn.neud.itms.common.exception;

import cn.dev33.satoken.exception.NotLoginException;
import cn.neud.itms.common.result.Result;
import cn.neud.itms.common.result.ResultCodeEnum;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

//AOP 面向切面
@RestControllerAdvice //返回json数据
public class GlobalExceptionHandler {

    @ExceptionHandler(Exception.class) //异常处理器
    public Result error(Exception e) {
        e.printStackTrace();
        return Result.fail(null);
    }

    //自定义异常处理
    @ExceptionHandler(ItmsException.class)
    public Result error(ItmsException exception) {
        return Result.build(null, exception.getCode(), exception.getMessage());
    }

    //自定义异常处理
    @ExceptionHandler(NotLoginException.class)
    public Result error(NotLoginException exception) {
        return Result.build(null, ResultCodeEnum.LOGIN_AUTH.getCode(), "请先登录");
    }
}
