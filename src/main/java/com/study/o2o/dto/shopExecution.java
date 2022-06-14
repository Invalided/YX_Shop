package com.study.o2o.dto;

import java.util.List;

import com.study.o2o.entity.Shop;
import com.study.o2o.enums.shopStateEnum;

public class shopExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	//店铺数量(增删改店铺时使用)
	private int count;
	//操作的shop(增删改店铺时使用)
	private Shop shop;
	//shop列表(查询店铺列表的时候使用)
	private List<Shop> shopList;
	
	public shopExecution() {
		
	}
	//店铺操作失败所使用构造器
	public shopExecution(shopStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//店铺操作成功所使用构造器(单个店铺)
	public shopExecution(shopStateEnum stateEnum,Shop shop) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shop = shop;
	}
	//店铺操作成功所使用构造器(批量店铺) 
	public shopExecution(shopStateEnum stateEnum,List<Shop> shopList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.shopList = shopList;
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
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public Shop getShop() {
		return shop;
	}
	public void setShop(Shop shop) {
		this.shop = shop;
	}
	public List<Shop> getShopList() {
		return shopList;
	}
	public void setShopList(List<Shop> shopList) {
		this.shopList = shopList;
	}
}
