package com.mark.usercenterbackend.common;

public enum ErrorCode {
    PARAMS_ERROR(40000,"参数错误", ""),
    NO_AUTH(40100, "无权限访问", ""),
    NO_LOGIN(40101, "未登录", ""),
    SYSTEM_ERROR(50000, "系统错误", ""),
    SUCCESS(200, "success", "success");


    private int code;

    private String message;

    private String description;

    ErrorCode(int code,String message,String description){
        this.code = code;
        this.message = message;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
