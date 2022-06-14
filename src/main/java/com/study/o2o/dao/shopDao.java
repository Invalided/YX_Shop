package com.study.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.Shop;

public interface shopDao {
	/**
	 * @param 用于区分参数 当参数大于1个时 则需要此标签
	 * 分页查询店铺，可输入的条件有：店铺名(模糊),店铺状态,店铺类别,区域Id,owner
	 * @param shopCondition 查询条件
	 * @param rowIndex 从第几行开始取数据
	 * @param pageSize 返回的数据条数
	 * @return
	 */
	List<Shop> queryShopList(@Param("shopCondition") Shop shopCondition,@Param("rowIndex") int rowIndex,@Param("pageSize") int pageSize);
	/**
	 * 返回queryShopList总数
	 * @param shopCondition
	 * @return
	 */
	int queryShopCount(@Param("shopCondition") Shop shopCondition);
	/**
	 * 通过id查询商铺
	 * @param shopId
	 * @return
	 */
	Shop queryByShopId(long shopId);
	/**
	 * 新增店铺 成功返回1,失败返回-1
	 * @param shop
	 * @return
	 */
	int insertShop(Shop shop);
	/**
	 * 更新店铺
	 * @param shop
	 * @return
	 */
	int updateShop(Shop shop);
}
