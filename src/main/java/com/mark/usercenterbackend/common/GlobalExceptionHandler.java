package com.mark.usercenterbackend.common;

import org.apache.ibatis.jdbc.Null;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(BusinessException.class)
    public BaseResponse<Null> businessExceptionHandler(BusinessException be){
        ErrorCode errorCode = be.getErrorCode();
        return ResultUtil.error(errorCode,be.getDescription());
    }
}
