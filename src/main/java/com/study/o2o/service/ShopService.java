package com.study.o2o.service;

import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.shopExecution;
import com.study.o2o.entity.Shop;
import com.study.o2o.exceptions.shopOperationException;

public interface ShopService {
	/**
	 * 根据shopCondition 分页返回列表数据
	 * @param ShopCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	public shopExecution getShopList(Shop ShopCondition,int pageIndex,int pageSize);
	/**
	 * 通过店铺Id获取店铺信息
	 * @param shopId
	 * @return
	 */
	Shop getByShopId(long shopId);
	/**
	 * 更新店铺信息 包括对图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws shopOperationException
	 */
	shopExecution modifyShop(Shop shop,imageHolder thumbnail) throws shopOperationException;
	/**
	 * 注册店铺信息 包括图片的处理
	 * @param shop
	 * @param shopImgInputStream
	 * @param fileName
	 * @return
	 * @throws shopOperationException
	 */
	shopExecution addShop(Shop shop,imageHolder thumbnail) throws shopOperationException;
}
