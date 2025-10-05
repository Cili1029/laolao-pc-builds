package com.laolao.exception;

/**
 * 账号已存在异常
 */
public class UnknownError extends BaseException {
    public UnknownError() {

    }

    public UnknownError(String message) {
        super(message);
    }
}
