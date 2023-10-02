package com.mark.usercenterbackend.controller;

import com.mark.usercenterbackend.common.BaseResponse;
import com.mark.usercenterbackend.common.BusinessException;
import com.mark.usercenterbackend.common.ErrorCode;
import com.mark.usercenterbackend.common.ResultUtil;
import com.mark.usercenterbackend.constant.UserConstant;
import com.mark.usercenterbackend.model.domain.User;
import com.mark.usercenterbackend.model.request.SearchUserRequest;
import com.mark.usercenterbackend.model.request.UserLoginRequest;
import com.mark.usercenterbackend.model.request.UserRegisterRequest;
import com.mark.usercenterbackend.service.UserService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

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
    public BaseResponse<Long> userRegister(@RequestBody @Validated UserRegisterRequest registerRequest){
        if(Objects.isNull(registerRequest)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册参数为空");
        }
        String userAccount = registerRequest.getUserAccount();
        String userPassword = registerRequest.getUserPassword();
        String confirmPassword = registerRequest.getConfirmPassword();
        String invitedCode = registerRequest.getInvitedCode();
        if(StringUtils.isAnyBlank(userAccount,userPassword,confirmPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册参数为空");
        }
        return userService.userRegister(userAccount,userPassword, confirmPassword,invitedCode);
    }

    @PostMapping("/login")
    public BaseResponse<User> userLogin(@RequestBody @Validated UserLoginRequest loginRequest, HttpServletRequest request){
        if(Objects.isNull(loginRequest)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "登录参数为空");
        }
        String userAccount = loginRequest.getUserAccount();
        String userPassword = loginRequest.getUserPassword();
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "登录参数为空");
        }
        return userService.userLogin(userAccount,userPassword, request);
    }

    @PostMapping("/searchUsers")
    public BaseResponse<List<User>> searchUsers(@RequestBody SearchUserRequest request, HttpServletRequest servletRequest){
        User loginUser = (User) servletRequest.getSession().getAttribute(UserConstant.userLoginState);
        //判断登录
        if(Objects.isNull(loginUser)){
            throw new BusinessException(ErrorCode.NO_LOGIN, "请先登录");
        }
        //鉴权
        if(!loginUser.getUserRole().equals(UserConstant.adminRole)){
            throw new BusinessException(ErrorCode.NO_AUTH, "您没有权限访问此页面");
        }
        return userService.searchUsersByCondition(request);
    }

    @PostMapping("/logout")
    public BaseResponse<Long> logout(HttpServletRequest request){
        request.getSession().removeAttribute(UserConstant.userLoginState);
        return ResultUtil.success(1L);
    }

    @GetMapping("/currentUser")
    public BaseResponse<User> currentUser(HttpServletRequest request){
        User loginUser = (User) request.getSession().getAttribute(UserConstant.userLoginState);
        if(Objects.isNull(loginUser)){
            throw new BusinessException(ErrorCode.NO_LOGIN, "请先登录");
        }

        return ResultUtil.success(loginUser);
    }
}
