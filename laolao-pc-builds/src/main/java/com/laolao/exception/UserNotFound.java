package com.laolao.exception;

/**
 * 账号不存在异常
 */
public class UserNotFound extends BaseException {

    public UserNotFound() {
    }

    public UserNotFound(String msg) {
        super(msg);
    }

}
