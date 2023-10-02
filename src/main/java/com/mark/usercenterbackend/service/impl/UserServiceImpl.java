package com.mark.usercenterbackend.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.usercenterbackend.constant.UserConstant;
import com.mark.usercenterbackend.model.domain.User;
import com.mark.usercenterbackend.model.request.SearchUserRequest;
import com.mark.usercenterbackend.service.UserService;
import com.mark.usercenterbackend.mapper.UserMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import org.springframework.validation.annotation.Validated;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
* @author lenovo
* @description 针对表【user】的数据库操作Service实现
* @createDate 2023-09-28 10:46:42
*/
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User>
    implements UserService{
    @Resource
    private UserMapper userMapper;

    @Override
    public long userRegister(String userAccount, String userPassword, String confirmPassword) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,confirmPassword)){
            return -1;
        }

        if(!userAccount.matches(UserConstant.accountRegex)){
            return -1;
        }

        if(!userPassword.matches(UserConstant.passwordRegex)){
            return -1;
        }

        if(!userPassword.equals(confirmPassword)){
            return -1;
        }

        //2. 查询账号是否被注册
        User selectUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", userAccount));
        if(!Objects.isNull(selectUser)){
            return -1;
        }

        //3. 对密码进行md5加密
        String newPassword = DigestUtils.md5DigestAsHex((UserConstant.passwordSalt + userPassword).getBytes(StandardCharsets.UTF_8));

        //4. 封装user对象
        User user = new User();
        user.setUsername(userAccount);
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);

        //5. 插入数据
        int insert = userMapper.insert(user);
        if(insert < 0){
            return -1;
        }

        return 1;
    }

    @Override
    public User userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            return null;
        }

        if(!userAccount.matches(UserConstant.accountRegex)){
            return null;
        }

        if(!userPassword.matches(UserConstant.passwordRegex)){
            return null;
        }
        //2. 查询账户是否存在
        User selectUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", userAccount));
        if(Objects.isNull(selectUser)){
            return null;
        }
        //3. 校验密码
        String newPassword = DigestUtils.md5DigestAsHex((UserConstant.passwordSalt + userPassword).getBytes(StandardCharsets.UTF_8));

        if(!newPassword.equals(selectUser.getUserPassword())){
            return null;
        }
        //4. 脱敏
        User loginUser = getSafetyUser(selectUser);
        //5. 存储登录态到session
        request.getSession().setAttribute(UserConstant.userLoginState, loginUser);

        return loginUser;
    }

    @Override
    public List<User> searchUsersByCondition(SearchUserRequest searchUserRequest) {
        String username = searchUserRequest.getUsername();
        List<User> users = this.query().like("username", username).list();

        return users.stream().map(this::getSafetyUser).collect(Collectors.toList());
    }

    private User getSafetyUser(User selectUser) {
        selectUser.setUserPassword("");
        return selectUser;
    }
}




