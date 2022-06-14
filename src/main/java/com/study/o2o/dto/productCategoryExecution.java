package com.study.o2o.dto;

import java.util.List;

import com.study.o2o.entity.productCategory;
import com.study.o2o.enums.productCategoryStateEnum;

public class productCategoryExecution {
	//结果状态
	private int state;
	//状态标识
	private String stateInfo;
	
	private List<productCategory> productCategoryList;
	
	public productCategoryExecution() {
		
	}
	//失败时使用的构造器
	public productCategoryExecution(productCategoryStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	//成功时使用的构造器
	public productCategoryExecution(productCategoryStateEnum stateEnum,List<productCategory> productCategoryList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.productCategoryList = productCategoryList;
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
	public List<productCategory> getProductCategoryList() {
		return productCategoryList;
	}
	public void setProductCategoryList(List<productCategory> productCategoryList) {
		this.productCategoryList = productCategoryList;
	}
	
}
