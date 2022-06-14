package com.study.o2o.enums;

public enum shopStateEnum {
	CHECK(0,"审核中"),OFFLINE(-1,"非法店铺"),SUCCESS(1,"操作成功"),
	PASS(2,"通过认证"),INNER_ERROR(-1001,"内部系统错误"),
	NULL_SHOPID(-1002,"ShopId为空"),NULL_SHOP(-1003,"Shop信息为空");
	private int state;
	private String stateInfo;
	
	private shopStateEnum(int state,String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}
	public int getState() {
		return state;
	}
	public String getStateInfo() {
		return stateInfo;
	}
	/**
	 * 依据传入的state返回相应的enum值
	 */ 
	public static shopStateEnum stateOf(int state) {
		for(shopStateEnum stateEnum:values()) {
			if(stateEnum.getState()==state) {
				return stateEnum;
			}
		}
		return null; 
	}
}

