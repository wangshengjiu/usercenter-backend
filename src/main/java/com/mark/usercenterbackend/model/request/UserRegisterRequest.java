package com.mark.usercenterbackend.model.request;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UserRegisterRequest implements Serializable {
    private static final long serialVersionUID = 1L;

    @NotNull(message = "账号不能为空")
    @Length(min = 4)
    private String userAccount;

    @NotNull(message = "密码不能为空")
    @Length(min = 8)
    private String userPassword;

    @NotNull(message = "确认密码不能为空")
    @Length(min = 8)
    private String confirmPassword;

    @Length(min = 8,max = 8)
    private String invitedCode;
}
