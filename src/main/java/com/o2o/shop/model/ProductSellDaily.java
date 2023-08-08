package com.o2o.shop.model;


import lombok.Data;

import java.util.Date;
@Data
//顾客消费的商品映射
public class ProductSellDaily {
	//主键Id
	private Long productSellDailyId;
	//哪天的销量，精确到天
	private Date createTime;
	//销量
	private Integer total;
	//商品信息实体类
	private ProductDO product;
	//店铺信息实体类
	private ShopDO shop;

}
