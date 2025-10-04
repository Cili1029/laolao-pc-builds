package com.laolao.exception;

/**
 * 账号已存在异常
 */
public class SmsCodeError extends BaseException {
    public SmsCodeError() {

    }

    public SmsCodeError(String message) {
        super(message);
    }
}
