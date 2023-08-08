package com.o2o.shop.validator.authentication;

import javax.validation.GroupSequence;

/**
 * @author 勿忘初心
 * @since 2023-07-11-20:32
 * 账户信息校验序列
 */
@GroupSequence({AuthCheckSequence.UserTypeCheck.class, AuthCheckSequence.UsernameCheck.class, AuthCheckSequence.PasswordCheck.class})
public interface AuthCheckSequence {

    /**
     * 用户类型
     */
    interface UserTypeCheck {};

    /**
     * 用户名校验
     */
    interface UsernameCheck {};

    /**
     * 密码校验
     */
    interface PasswordCheck {};
}
