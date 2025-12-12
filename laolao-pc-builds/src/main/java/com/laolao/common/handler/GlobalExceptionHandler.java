package com.laolao.common.handler;

import com.laolao.common.exception.BaseException;
import com.laolao.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器，处理项目中抛出的业务异常
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 捕获业务异常
     * @param baseException 捕获的异常
     * @return 异常信息
     */
    @ExceptionHandler
    public Result<String> exceptionHandler(BaseException baseException){
        return Result.error(baseException.getMessage());
    }

//    //处理SQL异常
//    @ExceptionHandler
//    public Result exceptionHandler(SQLIntegrityConstraintViolationException ex){
//        String message = ex.getMessage();
//        if (message.contains("Duplicate entry")){
//            String[] s = message.split(" ");
//            String username = s[2];
//            String msg = username + MessageConstant.USER_ALREADY_EXISTS;
//            return Result.error(msg);
//        }
//        return Result.error("位置错误");
//    }
}
