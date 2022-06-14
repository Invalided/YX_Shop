package com.study.o2o.dto;

import java.util.TreeSet;

/**
 * 配合echart的xAxis项
 * 
 * @author Administrator
 *
 */
public class EchartXAxis {
	private String type = "category";
	//去重操作 使用TreeSet 保证顺序
	private TreeSet<String> data;
	
	public String getType() {
		return type;
	}
	
	public TreeSet<String> getData() {
		return data;
	}
	public void setData(TreeSet<String> data) {
		this.data = data;
	}
}
