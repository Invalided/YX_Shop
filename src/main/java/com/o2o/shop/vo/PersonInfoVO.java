package com.o2o.shop.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-07-14:44
 * 个人信息VO对象
 */
@Data
public class PersonInfoVO {
    /**
     * 用户Id
     */
    private Integer userId;

    /**
     * 昵称
     */
    private String name;

    /**
     * 用户邮箱
     */
    private String email;

    /**
     * 用户性别
     */
    private String gender;

    /**
     * 0:账号封禁,1:普通用户
     */
    private Integer enableStatus;

    /**
     * 1.顾客，2.店家，3.超级管理员
     */
    private Integer userType;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
