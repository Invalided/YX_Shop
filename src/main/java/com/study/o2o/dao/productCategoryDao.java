package com.study.o2o.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.study.o2o.entity.productCategory;

public interface productCategoryDao {
	/**
	 * 通过shop id 查询店铺商品类别
	 * @param shopId
	 * @return
	 */
	 List<productCategory> queryProductCategoryList(long shopId);
	 /**
	  * 批量新增商品类别
	  * @param productCategoryList
	  * @return
	  */
	 int batchInsertProductCategory(@Param("productCategoryList") List<productCategory> productCategoryList);
	 /**
	  * 删除指定商品类别
	  * @param productCategory
	  * @param shopId
	  * @return
	  */
	 int deleteProductCategory(@Param("productCategoryId") long productCategory,@Param("shopId") long shopId);
}
