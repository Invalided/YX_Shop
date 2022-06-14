package com.study.o2o.config;

import org.springframework.beans.factory.annotation.Autowired;

import com.study.o2o.service.ProductSellDailyService;

public class TestQuartz {
	@Autowired
	private ProductSellDailyService productSellDailyService;
	
	public void executeMethod() {
		System.out.println("----------3秒执行一次----------");
		//执行定时任务
		productSellDailyService.dailyCalculate();
	}
}
