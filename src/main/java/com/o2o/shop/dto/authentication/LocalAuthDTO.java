package com.o2o.shop.dto.authentication;

import com.o2o.shop.validator.authentication.AuthChangeSequence;
import com.o2o.shop.validator.authentication.AuthCheckSequence;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

/**
 * @author 勿忘初心
 * @since 2023-07-11-18:09
 * 账号信息DTO类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocalAuthDTO {

    /**
     * 账号Id
     */
    @NotNull(message = "localAuthId不能为空")
    private Integer localAuthId;

    /**
     * 用户Id
     */
    @NotNull(message = "用户id不能为空",groups = AuthChangeSequence.UserIdCheck.class)
    @Range(min = 1,max = Integer.MAX_VALUE,message = "用户Id不合法",groups = AuthChangeSequence.UserIdCheck.class)
    private Integer userId;

    /**
     * 用户类型 1.用户 2.商家 3.管理员,目前仅支持用户和商家类型注册
     */
    @NotNull(message = "用户类型不能为空",groups = AuthCheckSequence.UserTypeCheck.class)
    @Range(min = 1,max = 3,message = "用户类型不合法",groups = AuthCheckSequence.UserTypeCheck.class)
    private Integer userType;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空",groups = AuthCheckSequence.UsernameCheck.class)
    @Pattern(regexp = "^[a-zA-Z0-9]{3,8}$", message = "用户名必须由3-8个字母或数字组成",
    groups = AuthCheckSequence.UsernameCheck.class)
    private String username;

    /**
     * 密码
     */
    @NotBlank(message = "密码不能为空",groups = AuthCheckSequence.PasswordCheck.class)
    @Size(min = 6,max = 18, message = "密码长度必须在6到18位之间",groups = AuthCheckSequence.PasswordCheck.class)
    private String password;

    /**
     * 新密码，修改密码时使用
     */
    @NotBlank(message = "新密码不能为空",groups = AuthChangeSequence.ChangePassword.class)
    @Size(min = 6,max = 18, message = "新密码长度必须在6到18位之间",groups = AuthChangeSequence.ChangePassword.class)
    private String newPassword;
}
