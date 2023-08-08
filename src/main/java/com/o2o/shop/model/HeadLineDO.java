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
 * @Accessors (chain = true) 注解用于表示该类中的set方法返回的是对象，而非无返回值。
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_head_line")
@JsonIgnoreProperties({"version","deleted"})
public class HeadLineDO {

    /**
     * 头条Id
     */
    @TableId(value = "line_id")
    private Integer lineId;

    /**
     * 头条名称
     */
    @TableField("line_name")
    private String lineName;

    /**
     * 头条链接
     */
    @TableField("line_link")
    private String lineLink;

    /**
     * 头条图片
     */
    @TableField("line_img")
    private String lineImg;

    /**
     * 头条权重
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 启用状态
     */
    @TableField("enable_status")
    private Integer enableStatus;

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
