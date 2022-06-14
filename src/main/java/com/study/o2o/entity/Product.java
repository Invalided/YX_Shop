package com.study.o2o.entity;

import java.util.Date;
import java.util.List;

public class Product {
	//商品id
	private Long productId;
	//商品名称
	private String productName;
	//商品描述
	private String productDesc;
	//缩略图
	private String imgAddr;
	//原价
	private String normalPrice;
	//折扣价
	private String promotionPrice;
	//权重
	private Integer priority;
	//商品积分
	private Integer point;
	//创建时间
	private Date createTime;
	//更新时间
	private Date lastEditTime;
	//0.下架 1.在前端系统展示
	private Integer enableStatus;
	//商品详情图片列表 商品与详情图为一对多关系
	private List<productImg> productImgList;
	//商品类别
	private productCategory productCategory;
	//商品对应店铺
	private Shop shop;
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String prouductName) {
		this.productName = prouductName;
	}
	public String getProductDesc() {
		return productDesc;
	}
	public void setProductDesc(String productDesc) {
		this.productDesc = productDesc;
	}
	public String getImgAddr() {
		return imgAddr;
	}
	public void setImgAddr(String imgAddr) {
		this.imgAddr = imgAddr;
	}
	public String getNormalPrice() {
		return normalPrice;
	}
	public void setNormalPrice(String normalPrice) {
		this.normalPrice = normalPrice;
	}
	public String getPromotionPrice() {
		return promotionPrice;
	}
	public void setPromotionPrice(String promotionPrice) {
		this.promotionPrice = promotionPrice;
	}
	public Integer getPriority() {
		return priority;
	}
	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
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
	public List<productImg> getProductImgList() {
		return productImgList;
	}
	public void setProductImgList(List<productImg> productImgList) {
		this.productImgList = productImgList;
	}
	public productCategory getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(productCategory prouductCategory) {
		this.productCategory = prouductCategory;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	
}
