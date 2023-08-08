package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户信息表
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_person_info")
@JsonIgnoreProperties({"version","deleted"})
public class PersonInfoDO {

    /**
     * 用户Id
     */
    @TableId(value = "user_id")
    private Integer userId;

    /**
     * 昵称
     */
    @TableField("name")
    private String name;

    /**
     * 用户头像
     */
    @TableField("profile_img")
    private String profileImg;

    /**
     * 用户邮箱
     */
    @TableField("email")
    private String email;

    /**
     * 用户性别
     */
    @TableField("gender")
    private String gender;

    /**
     * 0:账号封禁,1:普通用户
     */
    @TableField("enable_status")
    private Integer enableStatus;

    /**
     * 1.顾客，2.店家，3.超级管理员
     */
    @TableField("user_type")
    private Integer userType;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "last_edit_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 版本号,用于乐观锁实现
     */
    @TableField("version")
    @Version
    private Integer version;

    /**
     * 逻辑删除:0.未删除 1.已删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
}
