package com.hmall.smarthome.common;

/**
 * 响应码
 */
public enum ResponseCode {

    SUCCESS(200, "操作成功"),
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    INVALID_PARAMETER(400, "参数无效"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源未找到"),
    USER_EXISTS(500, "用户已存在");

    private final int code;
    private final String message;

    ResponseCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
