package com.o2o.shop.model;

import java.util.Date;

//顾客已领取的奖品映射
public class UserAwardMap {
	//主键Id
	private Long userAwardId;
	//创建时间
	private Date createTime;
	//使用状态 0.未兑换 1.已兑换
	private Integer usedStatus;
	//领取奖品所消耗的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfoDO user;
	//奖品信息实体类
	private AwardDO award;
	//店铺信息实体类
	private ShopDO shop;
	//操作员(店员)信息实体类
	private PersonInfoDO operator;
}
