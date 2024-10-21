package com.hmall.smarthome.exception;


import com.hmall.smarthome.common.BaseResponse;
import com.hmall.smarthome.common.ResponseCode;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理类
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    // 捕获所有的异常
    @ExceptionHandler(Exception.class)
    public BaseResponse<?> handleException(Exception e) {
        // 打印错误日志
        e.printStackTrace();
        // 返回通用错误响应
        return BaseResponse.error(ResponseCode.INTERNAL_SERVER_ERROR);
    }

    // 捕获自定义的业务异常
    @ExceptionHandler(CustomException.class)
    public BaseResponse<?> handleCustomException(CustomException e) {
        return BaseResponse.error(e.getResponseCode());
    }

    // 捕获非法参数异常
    @ExceptionHandler(IllegalArgumentException.class)
    public BaseResponse<?> handleIllegalArgumentException(IllegalArgumentException e) {
        return BaseResponse.error(ResponseCode.INVALID_PARAMETER);
    }
}
