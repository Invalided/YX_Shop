package com.study.o2o.util;

public class pageCalculator {
	public static int calculateRowIndex(int pageIndex,int pageSize) {
		//返回一个页面所需要显示的列表数
		return (pageIndex>0)?(pageIndex-1)*pageSize:0;
	}
}
