package com.study.o2o.service;

import com.study.o2o.dto.UserShopMapExecution;
import com.study.o2o.entity.UserShopMap;

public interface UserShopMapService {
	/**
	 * 根据传入的查询分页信息查询用户积分列表
	 * 
	 * @param usershopMapCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition,int pageIndex,int pageSize);
	/**
	 *根据用户Id和店铺Id获取用户在某个店铺下的积分情况
	 *
	 * @param userId
	 * @param shopId
	 * @return
	 */
	UserShopMap getUserShopMap(long userId,long shopId);
}