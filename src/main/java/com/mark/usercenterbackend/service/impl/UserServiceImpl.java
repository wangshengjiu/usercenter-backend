package com.mark.usercenterbackend.service.impl;
import java.util.Date;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mark.usercenterbackend.common.BaseResponse;
import com.mark.usercenterbackend.common.BusinessException;
import com.mark.usercenterbackend.common.ErrorCode;
import com.mark.usercenterbackend.common.ResultUtil;
import com.mark.usercenterbackend.constant.UserConstant;
import com.mark.usercenterbackend.model.domain.User;
import com.mark.usercenterbackend.model.request.SearchUserRequest;
import com.mark.usercenterbackend.service.UserService;
import com.mark.usercenterbackend.mapper.UserMapper;
import com.mark.usercenterbackend.utils.RandomInviteCodeGenerator;
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
    public BaseResponse<Long> userRegister(String userAccount, String userPassword, String confirmPassword, String invitedCode) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword,confirmPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "注册参数为空");
        }

        if(!userAccount.matches(UserConstant.accountRegex)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不符合规范");
        }

        if(!userPassword.matches(UserConstant.passwordRegex)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不符合规范");
        }

        if(!userPassword.equals(confirmPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "两次输入的密码不一致");
        }

        //2. 查询账号是否被注册
        User selectUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", userAccount));
        if(!Objects.isNull(selectUser)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "该账号已被注册");
        }

        //3. 如果用户填了邀请码，校验邀请码是否正确
        if(!Objects.isNull(invitedCode)){
            User invitedUser = userMapper.selectOne(new QueryWrapper<User>().eq("invited_code", invitedCode));
            if(Objects.isNull(invitedUser)){
                throw new BusinessException(ErrorCode.PARAMS_ERROR, "该邀请码不存在");
            }
        }

        //4. 对密码进行md5加密
        String newPassword = DigestUtils.md5DigestAsHex((UserConstant.passwordSalt + userPassword).getBytes(StandardCharsets.UTF_8));

        //生成邀请码
        String loginUserInvitedCode = RandomInviteCodeGenerator.generateShortTimestampInviteCode();
        //5. 封装user对象
        User user = new User();
        user.setUsername(userAccount);
        user.setUserAccount(userAccount);
        user.setUserPassword(newPassword);
        user.setInvitedCode(loginUserInvitedCode);

        //6. 插入数据
        int insert = userMapper.insert(user);
        if(insert < 0){
            throw new BusinessException(ErrorCode.SYSTEM_ERROR, "注册失败，请稍后重试");
        }

        return ResultUtil.success(1L);
    }

    @Override
    public BaseResponse<User> userLogin(String userAccount, String userPassword, HttpServletRequest request) {
        //1. 校验
        if(StringUtils.isAnyBlank(userAccount,userPassword)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "登录参数为空");
        }

        if(!userAccount.matches(UserConstant.accountRegex)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不符合规范");
        }

        if(!userPassword.matches(UserConstant.passwordRegex)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码不符合规范");
        }
        //2. 查询账户是否存在
        User selectUser = userMapper.selectOne(new QueryWrapper<User>().eq("user_account", userAccount));
        if(Objects.isNull(selectUser)){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "账号不存在");
        }
        //3. 校验密码
        String newPassword = DigestUtils.md5DigestAsHex((UserConstant.passwordSalt + userPassword).getBytes(StandardCharsets.UTF_8));

        if(!newPassword.equals(selectUser.getUserPassword())){
            throw new BusinessException(ErrorCode.PARAMS_ERROR, "密码错误，请重新输入");
        }
        //4. 脱敏
        User loginUser = getSafetyUser(selectUser);
        //5. 存储登录态到session
        request.getSession().setAttribute(UserConstant.userLoginState, loginUser);

        return ResultUtil.success(loginUser);
    }

    @Override
    public BaseResponse<List<User>> searchUsersByCondition(SearchUserRequest searchUserRequest) {
        String username = searchUserRequest.getUsername();
        List<User> users = this.query().like(!Objects.isNull(username), "username", username).list();

        List<User> userList = users.stream().map(this::getSafetyUser).collect(Collectors.toList());

        return ResultUtil.success(userList);
    }

    private User getSafetyUser(User selectUser) {
        selectUser.setUserPassword("");
        return selectUser;
    }
}




