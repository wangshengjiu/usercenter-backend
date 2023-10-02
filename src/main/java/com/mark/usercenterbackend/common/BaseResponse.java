package com.mark.usercenterbackend.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class BaseResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer code;

    private String message;

    private String description;

    private T data;

    public BaseResponse(Integer code,String message, String description, T data){
        this.code = code;
        this.message = message;
        this.description = description;
        this.data = data;
    }

    public BaseResponse(ErrorCode errorCode,String description){
        this(errorCode.getCode(), errorCode.getMessage(),description,null);
    }

}
