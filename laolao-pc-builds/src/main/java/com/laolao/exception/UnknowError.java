package com.laolao.exception;

/**
 * 账号已存在异常
 */
public class UnknowError extends BaseException {
    public UnknowError() {

    }

    public UnknowError(String message) {
        super(message);
    }
}
