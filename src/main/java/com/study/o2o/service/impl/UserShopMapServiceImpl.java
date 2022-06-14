package com.study.o2o.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.study.o2o.dao.UserShopMapDao;
import com.study.o2o.dto.UserShopMapExecution;
import com.study.o2o.entity.UserShopMap;
import com.study.o2o.service.UserShopMapService;
import com.study.o2o.util.pageCalculator;

@Service
public class UserShopMapServiceImpl implements UserShopMapService{
	
	@Autowired
	private UserShopMapDao userShopMapDao;
	
	@Override
	public UserShopMapExecution listUserShopMap(UserShopMap userShopMapCondition, int pageIndex, int pageSize) {
		//空值判断
		if(userShopMapCondition != null && pageIndex != -1 && pageSize != -1) {
			//页转行
			int beginIndex = pageCalculator.calculateRowIndex(pageIndex, pageSize);
			List<UserShopMap> userShopMapList = userShopMapDao.queryUserShopMapList(userShopMapCondition, beginIndex, pageSize);
			//返回总数
			int count = userShopMapDao.queryUserShopMapCount(userShopMapCondition);
			UserShopMapExecution ue = new UserShopMapExecution();
			ue.setUserShopMapList(userShopMapList);
			ue.setCount(count);
			return ue;
		}else {
			return null;
		}
	}

	@Override
	public UserShopMap getUserShopMap(long userId, long shopId) {
		return userShopMapDao.queryUserShopMap(userId, shopId);
	}

}
