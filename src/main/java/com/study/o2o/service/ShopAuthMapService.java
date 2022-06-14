package com.study.o2o.service;

import com.study.o2o.dto.ShopAuthMapExecution;
import com.study.o2o.entity.ShopAuthMap;
import com.study.o2o.exceptions.ShopAuthMapOperationException;

public interface ShopAuthMapService {
	/**
	 * 根据店铺Id分页显示该店铺的授权信息
	 * 
	 * @param shopId
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	ShopAuthMapExecution listShopAuthMapByShopId(Long shopId,Integer pageIndex,Integer pageSize);
	/**
	 * 根据ShopAuthId返回对应的授权信息
	 * 
	 * @param shopAuthId
	 * @return
	 */
	ShopAuthMap getShopAuthMapById(Long shopAuthId);
	/**
	 * 添加授权信息
	 * 
	 * @param shopAuthMap
	 * @return
	 * @throws ShopAuthMapOperationException
	 */
	ShopAuthMapExecution addShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;
	/**
	 * 更新授权信息，包括职位，状态等
	 * 
	 * @param shopAuthMap
	 * @return
	 * @throws ShopAuthMapOperationException
	 */
	ShopAuthMapExecution modifyShopAuthMap(ShopAuthMap shopAuthMap) throws ShopAuthMapOperationException;
}
	