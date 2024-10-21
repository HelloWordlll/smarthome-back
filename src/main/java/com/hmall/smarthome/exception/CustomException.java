package com.hmall.smarthome.exception;


import com.hmall.smarthome.common.ResponseCode;

/**
 * 自定义异常
 */
public class CustomException extends RuntimeException {

    private final ResponseCode responseCode;

    public CustomException(ResponseCode responseCode) {
        super(responseCode.getMessage());
        this.responseCode = responseCode;
    }

    public ResponseCode getResponseCode() {
        return responseCode;
    }
}
