package com.hmall.smarthome.common;

/**
 * 响应工厂类
 */
public class ResponseFactory {

    // 成功响应
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    // 错误响应
    public static <T> BaseResponse<T> error(ResponseCode code) {
        return new BaseResponse<>(code.getCode(), code.getMessage(), null);
    }

    // 自定义响应
    public static <T> BaseResponse<T> custom(int code, String message, T data) {
        return new BaseResponse<>(code, message, data);
    }
}
