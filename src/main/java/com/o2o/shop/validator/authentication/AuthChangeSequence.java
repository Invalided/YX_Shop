package com.o2o.shop.validator.authentication;

import javax.validation.GroupSequence;

/**
 * @author 勿忘初心
 * @since 2023-07-11-20:32
 * 账户信息修改校验序列
 */
@GroupSequence({AuthChangeSequence.UserIdCheck.class, AuthChangeSequence.PasswordCheck.class,
        AuthChangeSequence.ChangePassword.class})
public interface AuthChangeSequence {

    /**
     * 用户id校验
     */
    interface UserIdCheck {};

    /**
     * 密码校验
     */
    interface PasswordCheck {};

    /**
     * 修改密码
     */
    interface ChangePassword {};
}
