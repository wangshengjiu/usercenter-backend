package com.mark.usercenterbackend.service;
import java.util.Date;

import com.mark.usercenterbackend.UsercenterBackendApplication;
import com.mark.usercenterbackend.model.domain.User;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(classes = UsercenterBackendApplication.class)
class UserServiceTest {

    @Resource
    private UserService userService;

    @Test
    public void testAddUser(){
        User user = new User();
        user.setUsername("mark01");
        user.setUserAccount("mark01");
        user.setUserPassword("123456");
        user.setPhone("123456");
        user.setGender(1);
        user.setEmail("123456789@qq.com");

        userService.save(user);
    }

    @Test
    public void testRegisterUser(){
//        String userAccount = "";
//        String userPassword = "";
//        String confirmPassword = "";
//        // 账号小于四位
//        userAccount = "mmm";
//        userPassword = "12345678";
//        confirmPassword = "12345678";
//        long result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 密码小于八位
//        userAccount = "mmmmm";
//        userPassword = "123456";
//        confirmPassword = "123456";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 账号包含特殊字符
//        userAccount = "mmm%$^";
//        userPassword = "12345678";
//        confirmPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 密码包含特殊字符
//        userAccount = "mmmmmm";
//        userPassword = "12345678%^$";
//        confirmPassword = "12345678%^$";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 密码和确认密码不一致
//        userAccount = "mmmmmm";
//        userPassword = "12345678";
//        confirmPassword = "123456788654";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        //账号为空
//        userAccount = "";
//        userPassword = "12345678";
//        confirmPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 密码为空
//        userAccount = "1111111";
//        userPassword = "";
//        confirmPassword = "";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 用户名存在
//        userAccount = "mark01";
//        userPassword = "12345678";
//        confirmPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(-1, result);
//        // 注册成功
//        userAccount = "mark001";
//        userPassword = "12345678";
//        confirmPassword = "12345678";
//        result = userService.userRegister(userAccount, userPassword, confirmPassword);
//        assertEquals(1, result);
    }
}