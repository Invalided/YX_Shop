package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-06-21-17:25
 * 与数据库表做映射
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@TableName("user")
@JsonIgnoreProperties({"version","deleted"})
public class UserDO {
    /**
     * @TableId 注解指定该字段为主键,auto属性已在application.yml中配置
     */
    @TableId
    private Long id;
    /**
     * 用户名
     */
    private String username;
    /**
     * 用户密码
     */
    private String password;
    /**
     * 用户年龄
     */
    private Integer age;
    /***
     * @Version注解用于标识该字段实现乐观锁
     * 版本号
     */
    @Version
    private Integer version;
    /**
     * 结合handle自动注入当前time
     * INSERT表示仅在插入操作执行时填充
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Date createTime;
    /**
     * 结合handle自动注入当前time
     * INSERT_UPDATE表示在插入和更新操作执行时填充
     * 修改时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    /**
     * 逻辑删除标识,1已删除,0未删除
     */
    @TableLogic
    private Integer deleted;
}
