package com.mark.usercenterbackend.common;

public class ResultUtil {
    public static <T> BaseResponse<T> success(T data){
        return new BaseResponse<T>(200,"success","success",data);
    }

    public static <T> BaseResponse<T> error(ErrorCode errorCode,String description){
        return new BaseResponse<T>(errorCode,description);
    }
}
