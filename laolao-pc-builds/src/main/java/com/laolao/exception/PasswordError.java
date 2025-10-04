package com.laolao.exception;

/**
 * 账号已存在异常
 */
public class PasswordError extends BaseException {
    public PasswordError() {

    }

    public PasswordError(String message) {
        super(message);
    }
}
