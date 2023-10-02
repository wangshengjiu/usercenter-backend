package com.mark.usercenterbackend.controller;

import com.mark.usercenterbackend.constant.UserConstant;
import com.mark.usercenterbackend.model.domain.User;
import com.mark.usercenterbackend.model.request.SearchUserRequest;
import com.mark.usercenterbackend.model.request.UserLoginRequest;
import com.mark.usercenterbackend.model.request.UserRegisterRequest;
import com.mark.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/user")
public class UserController {
    @Resource
    private UserService userService;

    @PostMapping("/register")
    public long userRegister(@RequestBody @Validated UserRegisterRequest registerRequest){
        if(Objects.isNull(registerRequest)){
            return -1;
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword,confirmPassword)){
            return -1;
        }
        return userService.userRegister(userAccount,userPassword, confirmPassword);
    }

    @PostMapping("/login")
    public User userLogin(@RequestBody @Validated UserLoginRequest loginRequest, HttpServletRequest request){
        if(Objects.isNull(loginRequest)){
            return null;
        }
        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }
        return userService.userLogin(userAccount,userPassword, request);
    }

    @PostMapping("/searchUsers")
    public List<User> searchUsers(@RequestBody SearchUserRequest request, HttpServletRequest servletRequest){
        User loginUser = (User) servletRequest.getSession().getAttribute(UserConstant.userLoginState);
        //判断登录
        if(Objects.isNull(loginUser)){
            return null;
        }
        //鉴权
        if(!loginUser.getUserRole().equals(UserConstant.adminRole)){
            return null;
        }
        return userService.searchUsersByCondition(request);
    }

    @PostMapping("/logout")
    public long logout(HttpServletRequest request){
        request.getSession().removeAttribute(UserConstant.userLoginState);
        return 1;
    }
}
