package com.study.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.UserProductMap;

public interface UserProductMapDao {
	/**
	 * 根据查询条件分页返回用户购买的商品记录
	 * 
	 * @param userProductCondition
	 * @param rowIndex
	 * @param pageSize
	 * @return
	 */
	List<UserProductMap> queryUserProductMapList(@Param("userProductCondition") UserProductMap userProductCondition,@Param("rowIndex")int rowIndex,
			@Param("pageSize") int pageSize);
	/**
	 * 配合queryUserProductMapList根据相同的查询条件返回用户购买的商品记录总数
	 * 
	 * @param userProductMapCondition
	 * @return
	 */
	int queryUserProductMapCount(@Param("userProductCondition")UserProductMap userProductMapCondition);
	/**
	 * 添加一条用户购买商品的记录
	 * 
	 * @param userProductMap
	 * @return
	 */
	int insertUserProductMap(UserProductMap userProductMap);
}
