package com.study.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.shopCategory;

public interface shopCategoryDao {
	/**
	 * 根据传入的查询条件返回点店铺类别列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<shopCategory> queryShopCategory(@Param("shopCategoryCondition") 
	shopCategory shopCategoryCondition);
	/**
	 * 通过Id返回唯一的店铺类别信息
	 * @param shopCategoryId
	 * @return
	 */
	shopCategory queryShopCategoryById(long shopCategoryId);
	/**
	 * 根据传入的Id列表查询店铺类别信息(供超级管理员选定删除类别的时候用,主要是处理图片)
	 * @param shopCategoryIdList
	 * @return
	 */
	List<shopCategory> queryShopCategoryBYIds(List<Long> shopCategoryIdList);
	
	/**
	 * 插入一条店铺类别信息
	 * @param shopCategory
	 * @return
	 */
	int insertShopCategory(shopCategory shopCategory);
	/**
	 * 更新店铺类别信息
	 * 
	 * @param shopCategory
	 * @return
	 */
	int updateShopCategory(shopCategory shopCategory);
	/**
	 * 删除店铺类别
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	int deleteShopCategory(long shopCategoryId);
	/**
	 * 批量删除店铺类别
	 * @param shopCategoryIdList
	 * @return
	 */
	int batchDeleteShopCategory(List<Long> shopCategoryIdList);
}
