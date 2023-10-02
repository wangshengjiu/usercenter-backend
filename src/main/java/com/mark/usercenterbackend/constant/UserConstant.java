package com.mark.usercenterbackend.constant;

public interface UserConstant {
    String accountRegex = "^[a-zA-Z0-9_]{4,}$";

    String passwordRegex = "^[a-zA-Z0-9_]{8,}$";

    String passwordSalt = "user-center";

    String userLoginState = "userLoginState";

    Integer adminRole = 1;

    Integer commonRole = 1;
}
