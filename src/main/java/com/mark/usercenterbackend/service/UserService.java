package com.mark.usercenterbackend.service;

import com.mark.usercenterbackend.common.BaseResponse;
import com.mark.usercenterbackend.model.domain.User;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mark.usercenterbackend.model.request.SearchUserRequest;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
* @author lenovo
* @description 针对表【user】的数据库操作Service
* @createDate 2023-09-28 10:46:42
*/
public interface UserService extends IService<User> {
    /**
     * 用户注册方法
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param confirmPassword 确认密码
     * @param invitedCode 邀请码（选填）
     * @return long
     */
    BaseResponse<Long> userRegister(String userAccount, String userPassword, String confirmPassword, String invitedCode);

    /**
     * 用户登录方法
     * @param userAccount 用户账号
     * @param userPassword 用户密码
     * @param request request
     * @return User
     */
    BaseResponse<User> userLogin(String userAccount, String userPassword, HttpServletRequest request);

    /**
     * 根据条件查询用户方法
     * @param searchUserRequest 搜索条件
     * @return List
     */
    BaseResponse<List<User>> searchUsersByCondition(SearchUserRequest searchUserRequest);
}
