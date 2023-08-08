package com.o2o.shop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.Date;

/**
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 * @Accessors (chain = true) 注解用于表示该类中的set方法返回的是对象，而非无返回值。
 * HeadLineVO对象
 */
@Data
public class HeadLineVO {

    /**
     * 头条Id
     */
    private Integer lineId;

    /**
     * 头条名称
     */
    private String lineName;

    /**
     * 头条链接
     */
    private String lineLink;

    /**
     * 头条图片
     */

    private String lineImg;

    /**
     * 头条权重
     */
    private Integer priority;

    /**
     * 启用状态
     * @JsonInclude(JsonInclude.Include.NON_NULL) 该注解指明该对象为null时不返回
     */
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Integer enableStatus;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;
}
