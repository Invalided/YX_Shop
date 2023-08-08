package com.o2o.shop.service;

import com.o2o.shop.dto.authentication.LocalAuthDTO;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.ResultDataVO;

/**
 * 账号信息相关
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface LocalAuthService {

    /**
     * 用户注册服务
     * @param localAuthDTO 账号DTO对象
     * @return
     */
    ResultDataVO userRegister(LocalAuthDTO localAuthDTO);

    /**
     * 修改密码服务
     * @param localAuthDTO 账号DTO对象
     * @return
     */
    ResultDataVO userModifyPassword(LocalAuthDTO localAuthDTO);

    /**
     * 用户登录校验
     * @param localAuthDTO
     * @return
     */
    LocalAuthVO userLoginCheck(LocalAuthDTO localAuthDTO);

    /**
     * 用户退出登录
     * @return
     */
    ResultDataVO userLogout();

    /**
     * 根据当前请求获取用户存放的Session信息
     * @return
     */
    LocalAuthVO getUserAuthSession();
}
