package com.study.o2o.dto;

import java.util.List;

import com.study.o2o.entity.shopCategory;
import com.study.o2o.enums.shopCategoryStateEnum;

public class ShopCategoryExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	
	private shopCategory shopCategory;
	
	//操作的商铺类别
	private List<shopCategory> shopCategoryList;
	public ShopCategoryExecution() {
	}
	
	public ShopCategoryExecution(shopCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	public ShopCategoryExecution(shopCategoryStateEnum stateEnum,shopCategory shopCategory) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopCategory = shopCategory;
	}
	
	public ShopCategoryExecution(shopCategoryStateEnum stateEnum,List<shopCategory> shopCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopCategoryList = shopCategoryList;
	}

	public int getState() {
		return state;
	}

	public void setState(int state) {
		this.state = state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public void setStateInfo(String stateInfo) {
		this.stateInfo = stateInfo;
	}

	public shopCategory getShopCategory() {
		return shopCategory;
	}

	public void setShopCategory(shopCategory shopCategory) {
		this.shopCategory = shopCategory;
	}

	public List<shopCategory> getShopCategoryList() {
		return shopCategoryList;
	}

	public void setShopCategoryList(List<shopCategory> shopCategoryList) {
		this.shopCategoryList = shopCategoryList;
	}
}
