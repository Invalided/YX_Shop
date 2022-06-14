package com.study.o2o.service;

import com.study.o2o.dto.UserAwardMapExecution;
import com.study.o2o.entity.UserAwardMap;
import com.study.o2o.exceptions.UserAwardMapOperationException;

public interface UserAwardMapService {
	/**
	 * 根据传入的查询分页获取映射列表及总数
	 * 
	 * @param userAwardCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return
	 */
	UserAwardMapExecution listUserAwardMap(UserAwardMap userAwardCondition,Integer pageIndex,Integer pageSize);
	/**
	 * 根据 传入的Id获取映射信息
	 * 
	 * @param userAwardMapId
	 * @return
	 */
	UserAwardMap getUserAwardMapById(Long userAwardMapId);
	/**
	 * 领取奖品 添加映射信息
	 * @param userAwardMap
	 * @return
	 */
	UserAwardMapExecution addUserAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException;
	/**
	 * 修改映射信息， 这里主要修改奖品领取状态
	 * 
	 * @param userAwardMap
	 * @return
	 * @throws UserAwardMapOperationException
	 */
	UserAwardMapExecution modifyUserAwardMap(UserAwardMap userAwardMap) throws UserAwardMapOperationException;
}
