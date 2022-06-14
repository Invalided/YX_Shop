package com.study.o2o.entity;

import java.util.Date;

public class Shop {
	//商铺id
	private Long shopId;
	// 商铺ownerId
	private Long ownerId;
	//商铺名称
	private String shopName;
	//商铺描述
	private String shopDesc;
	//商铺地址
	private String shopAddr;
	//联系方式
	private String phone;
	//商铺图片(门面图)
	private String shopImg;
	//权重
	private Integer priority;
	//创建时间
	private Date createTime;
	//更新时间
	private Date lastEditTime;
	//-1.禁用  0.审核中 1.可用
	private Integer enableStatus;
	//超级管理员给店家的提醒
	private String advice;
	//区域ID
	private Area area;
	//用户ID
	private PersonInfo owner;
	//店铺类别ID
	private shopCategory shopCategory;
	public Long getShopId() {
		return shopId;
	}
	public void setShopId(Long shopId) {
		this.shopId = shopId;
	}
	public Long getOwnerId() {
		return ownerId;
	}
	public void setOwnerId(Long ownerId) {
		this.ownerId = ownerId;
	}
	public String getShopName() {
		return shopName;
	}
	public void setShopName(String shopName) {
		this.shopName = shopName;
	}
	public String getShopDesc() {
		return shopDesc;
	}
	public void setShopDesc(String shopDesc) {
		this.shopDesc = shopDesc;
	}
	public String getShopAddr() {
		return shopAddr;
	}
	public void setShopAddr(String shopAddr) {
		this.shopAddr = shopAddr;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getShopImg() {
		return shopImg;
	}
	public void setShopImg(String shopImg) {
		this.shopImg = shopImg;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public Date getLastEditTime() {
		return lastEditTime;
	}
	public void setLastEditTime(Date lastEditTime) {
		this.lastEditTime = lastEditTime;
	}
	public Integer getEnableStatus() {
		return enableStatus;
	}
	public void setEnableStatus(Integer enableStatus) {
		this.enableStatus = enableStatus;
	}
	public String getAdvice() {
		return advice;
	}
	public void setAdvice(String advice) {
		this.advice = advice;
	}
	public Area getArea() {
		return area;
	}
	public void setArea(Area area) {
		this.area = area;
	}
	public PersonInfo getOwner() {
		return owner;
	}
	public void setOwner(PersonInfo owner) {
		this.owner = owner;
	}
	public shopCategory getShopCategory() {
		return shopCategory;
	}
	public void setShopCategory(shopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}
	
	
}
