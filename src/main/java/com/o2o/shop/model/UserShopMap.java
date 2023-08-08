package com.o2o.shop.model;

import java.util.Date;

//顾客在此店铺下的总积分映射
public class UserShopMap {
	//主键Id
	private Long userShopId;
	//创建时间
	private Date createTime;
	//顾客在该店铺的积分
	private Integer point;
	//顾客信息实体类
	private PersonInfoDO user;
	//店铺信息实体类
	private ShopDO shop;
}
