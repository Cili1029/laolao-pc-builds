package com.laolao.exception;

/**
 * 账号已存在异常
 */
public class UserAlreadyExists extends BaseException {
    public UserAlreadyExists() {

    }

    public UserAlreadyExists(String message) {
        super(message);
    }
}
