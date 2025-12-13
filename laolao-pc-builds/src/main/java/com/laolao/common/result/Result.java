package com.laolao.common.result;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * 后端统一返回结果
 */
@Setter
@Getter
public class Result<T> implements Serializable {

    private Integer code; //编码：1成功，0为失败
    private String msg; //错误信息
    private T data; //数据

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(String msg) {
        Result<T> result = new Result<>();
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> success(T object) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        return result;
    }

    public static <T> Result<T> success(T object, String msg) {
        Result<T> result = new Result<T>();
        result.data = object;
        result.code = 1;
        result.msg = msg;
        return result;
    }

    public static <T> Result<T> error() {
        Result<T> result = new Result<>();
        result.code = 0;
        return result;
    }

    public static <T> Result<T> error(String msg) {
        Result result = new Result();
        result.msg = msg;
        result.code = 0;
        return result;
    }

}