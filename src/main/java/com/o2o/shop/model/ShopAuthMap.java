package com.o2o.shop.model;

import lombok.Data;

import java.util.Date;

@Data
//店铺授权
public class ShopAuthMap {
	//主键id
	private Long shopAuthId;
	//职务名称
	private String title;
	//职称符号(保留字段 可用于权限管理 0.管理店铺的权限 1.扫码的权限)
	private Integer titleFlag;
	//授权有效状态, 0.无效 1.有效
	private Integer enableStatus;
	//创建时间
	private Date createTime;
	//最近更新时间
	private Date lastEditTime;
	//员工信息实体类
	private PersonInfoDO employee;
	//店铺实体类
	private ShopDO shop;
}
