package com.hmall.smarthome.common;

/**
 * 基础响应类
 * @param <T>
 */
public class BaseResponse<T> {
    private int code;  // 状态码
    private String message;  // 消息
    private T data;  // 数据

    public BaseResponse() {}

    public BaseResponse(int code, String message, T data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    // 静态方法快速生成响应
    public static <T> BaseResponse<T> success(T data) {
        return new BaseResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), data);
    }

    public static <T> BaseResponse<T> success() {
        return new BaseResponse<>(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(ResponseCode responseCode) {
        return new BaseResponse<>(responseCode.getCode(), responseCode.getMessage(), null);
    }

    public static <T> BaseResponse<T> error(String msg) {
        return new BaseResponse<>(401, msg, null);
    }
}
