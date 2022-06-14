package com.study.o2o.enums;

public enum wechatAuthStateEnum {
	LOGINFAIL(-1, "openId输入有误"), SUCCESS(0, "操作成功"), NULL_AUTH_INFO(-1006, "注册信息为空");

	private int state;

	private String stateInfo;

	private wechatAuthStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static wechatAuthStateEnum stateOf(int index) {
		for (wechatAuthStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}
}
