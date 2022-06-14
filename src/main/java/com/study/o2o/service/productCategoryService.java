package com.study.o2o.service;

import java.util.List;

import com.study.o2o.dto.productCategoryExecution;
import com.study.o2o.entity.productCategory;
import com.study.o2o.exceptions.productCategoryOperationException;

public interface productCategoryService {
	/**
	 * 查询某个店铺下所有商品类别信息
	 * @param shopId
	 * @return
	 */
	List<productCategory> getProductCategoryList(long shopId);
	/**
	 * 批量添加商品类别
	 * @param productCategoryList
	 * @return
	 * @throws productCategoryOperationException
	 */
	productCategoryExecution batchAddProductCategory(List<productCategory> productCategoryList) throws productCategoryOperationException;

	/**
	 * 将商品类别下的商品里的类别id置为空，再删掉该商品类别
	 * @param productCategroy
	 * @param shopId
	 * @return
	 * @throws productCategoryOperationException
	 */
	productCategoryExecution deleteProductCategory(long productCategoryId,long shopId) throws productCategoryOperationException;

}
