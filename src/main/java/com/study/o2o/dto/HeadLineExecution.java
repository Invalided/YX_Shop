package com.study.o2o.dto;

import java.util.List;

import com.study.o2o.entity.headLine;
import com.study.o2o.enums.HeadLineStateEnum;

public class HeadLineExecution {

	//结果状态
	private int state;
	
	//状态标识
	private String stateInfo;
	
	//店铺数量
	private int count;
	
	//操作的headline(增删改商品的时候用)
	private headLine headLine;
	
	//获取的headLine列表(查询头条列表的时候用)
	private List<headLine> headLineList;
	
	// 失败的构造器
	public HeadLineExecution(HeadLineStateEnum stateEnum) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
	}
	
	//成功的构造器
	public HeadLineExecution(HeadLineStateEnum stateEnum,headLine headLine) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.headLine = headLine;
	}
	//成功的构造器
	public HeadLineExecution(HeadLineStateEnum stateEnum,List<headLine> headLineList) {
		this.state = stateEnum.getState();
		this.stateInfo = stateEnum.getStateInfo();
		this.headLineList = headLineList;
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

	public headLine getHeadLine() {
		return headLine;
	}

	public void setHeadLine(headLine headLine) {
		this.headLine = headLine;
	}

	public List<headLine> getHeadLineList() {
		return headLineList;
	}

	public void setHeadLineList(List<headLine> headLineList) {
		this.headLineList = headLineList;
	}
	
}
