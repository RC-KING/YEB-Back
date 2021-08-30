package com.jdd.server.exception;

import com.jdd.server.pojo.RespBean;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

/**
 * 全局异常处理
 * RestControllerAdvice 用于处理控制器中的异常
 * ErrorController可以处理所有的异常,包括没有进入Controller的异常,所以常用于处理没有进入控制器的异常
 * 两者结合使用
 */
@RestControllerAdvice
public class GlobalException {
    // 将异常SQLException.class交给该处理器处理
    @ExceptionHandler(SQLException.class)
    public RespBean mySqlException(SQLException e){
        e.printStackTrace();
        if (e instanceof SQLIntegrityConstraintViolationException){
            return RespBean.error("该数据存在关联数据,操作失败!");
        }
        // 还可以写很多...
        return RespBean.error("数据库异常,操作失败!");
    }

}
