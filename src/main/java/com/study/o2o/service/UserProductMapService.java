package com.study.o2o.service;

import java.util.List;

import com.study.o2o.dto.UserProductMapExecution;
import com.study.o2o.entity.UserProductMap;
import com.study.o2o.exceptions.UserProductMapOperationException;

public interface UserProductMapService {
	/**
	 * 通过传入的查询条件分页列出用户消费信息
	 * 
	 * @param userProductCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserProductMapExecution listUserProductMap(UserProductMap userProductCondition,Integer pageIndex,
			Integer pageSize);
	/**
	 * 添加一条用户消费记录
	 * @param userProductMap
	 * @return
	 * @throws UserProductMapOperationException
	 */
	UserProductMapExecution addUserProductMap(List<UserProductMap> userProductMapList) throws UserProductMapOperationException;
}
