package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;


/**
 *  @Author: 勿忘初心
 *
 *  使用@TableName指定数据库表名，如数据库表为user,此处类名为account,则应使用该注解@TableName(value="user")
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "tb_area")
@JsonIgnoreProperties(value = {"version","deleted"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class AreaDO {
	/**
	 * @TableId,id字段专属注解
	 * 区域Id
	 */
	@TableId(value = "area_id")
	private Integer areaId;

	/**
	 * 区域名称
	 */
	private String areaName;

	/**
	 * 区域权重
	 */
	private Integer priority;

	/**
	 * 创建时间
	 */
	@TableField(fill = FieldFill.INSERT)
	private Date createTime;

	/**
	 * 更新时间
	 * 注意实现填充效果，此处字段需要保持一致
	 * 方法1. updateTime => lastEditTime
	 * 方法2. 修改MyBatisPlusObjectHandler中的字段属性为lastEditTime
	 */

	@TableField(fill = FieldFill.INSERT_UPDATE,value = "last_edit_time")
	private Date updateTime;

	/**
	 * 版本号，实现乐观锁。
	 * @Version 注解用于标识该字段为乐观锁实现字段
	 */
	@Version
	private Integer version;

	/**
	 * 逻辑删除
	 */
	@TableLogic
	private Integer deleted;
}
