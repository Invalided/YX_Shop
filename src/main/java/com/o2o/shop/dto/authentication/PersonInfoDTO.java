package com.o2o.shop.dto.authentication;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author 勿忘初心
 * @since 2023-07-11-18:09
 * 用户信息实体类
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PersonInfoDTO {

    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 用户头像
     */
    private String profileImg;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 账号状态
     * 0:账号封禁,1:普通用户
     */
    private Integer enableStatus;

    /**
     * 1.顾客，2.商家
     */
    private Integer userType;
}
