package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 奖品实体类
 * @Author: 勿忘初心
 * @Date: 2023-07-23 13:12
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName(value = "tb_award")
@JsonIgnoreProperties(value = {"version","deleted"})
public class AwardDO {

	/**
	 * 奖品id
	 */
	@TableId(value = "award_id")
	private Integer awardId;
	/**
	 * 所属店铺id
	 */
	@TableField(value = "shop_id")
	private Integer shopId;
	/**
	 * 奖品名称
	 */
	@TableField(value = "award_name")
	private String awardName;
	/**
	 * 奖品描述
	 */
	@TableField(value = "award_desc")
	private String awardDesc;
	/**
	 * 奖品图片地址
	 */
	@TableField(value = "award_img")
	private String awardImg;
	/**
	 * 兑换所需积分
	 */
	@TableField(value = "point")
	private Integer point;
	/**
	 * 权重, 越大排名越前
	 */
	@TableField(value = "priority")
	private Integer priority;
	/**
	 * 创建时间
	 */
	@TableField(value = "create_time",fill = FieldFill.INSERT)
	private Date createTime;
	/**
	 * 更新时间
	 */
	@TableField(value = "last_edit_time",fill = FieldFill.INSERT_UPDATE)
	private Date updateTime;
	/**
	 * 可用状态 0.禁用 1.可用
	 */
	@TableField(value = "enable_status")
	private Integer enableStatus;

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
