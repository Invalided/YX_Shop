package com.study.o2o.enums;

public enum shopCategoryStateEnum {
	SUCCESS(0, "创建成功"), INNER_ERROR(-1001, "操作失败"), EMPTY(-1002, "区域信息为空");
	private int state;
	private String stateInfo;
	private shopCategoryStateEnum(int state, String stateInfo) {
		this.state = state;
		this.stateInfo = stateInfo;
	}

	public int getState() {
		return state;
	}

	public String getStateInfo() {
		return stateInfo;
	}

	public static shopCategoryStateEnum stateOf(int index) {
		for (shopCategoryStateEnum state : values()) {
			if (state.getState() == index) {
				return state;
			}
		}
		return null;
	}

}
