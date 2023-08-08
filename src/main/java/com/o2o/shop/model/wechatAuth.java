package com.o2o.shop.model;

import java.util.Date;

/**
 * 微信账号绑定
 */
public class wechatAuth {
	//微信帐号id
	private Long wechatAuthId;
	//openId
	private String openId;
	//创建时间
	private Date createTime;
	//用户实体类
	private PersonInfoDO personInfo;
	
}
