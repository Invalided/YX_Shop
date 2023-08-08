package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 * 账号权限表
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_local_auth")
@JsonIgnoreProperties({"version","deleted"})
public class LocalAuthDO {

    /**
     * 本地账号Id
     */
    @TableId(value = "local_auth_id")
    private Integer localAuthId;

    /**
     * 用户Id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 用户名
     */
    @TableField("username")
    private String username;

    /**
     * 密码
     */
    @TableField("password")
    private String password;

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
