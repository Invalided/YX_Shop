package com.study.o2o.service;

import java.util.List;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.productExecution;
import com.study.o2o.entity.Product;
import com.study.o2o.exceptions.productOperationException;

public interface productService {
	/**
	 * 添加商品信息以及图片处理
	 * @param product
	 * @param thumbnail
	 * @param productImageList
	 * @return
	 * @throws productOperationException
	 */
	productExecution addProduct(Product product,imageHolder thumbnail,List<imageHolder> productImageList) throws productOperationException;
	/**
	 * 通过商品Id查询唯一的商品信息
	 * @param product
	 * @return
	 */
	Product getProductById(long product);
 	/**
 	 * 修改商品信息以及图片处理
 	 * @param product
 	 * @param thumbnail
 	 * @param productImgHolderList
 	 * @return
 	 * @throws productOperationException
 	 */
	productExecution modifyProduct(Product product,imageHolder thumbnail,List<imageHolder> productImgHolderList)
			throws productOperationException;
	/**
	 * 查询商品列表并分页, 可输入的条件有: 商品名(模糊), 商品状态, 店铺Id, 商品类别
	 * @param productCondition
	 * @param pageIndex
	 * @param pageSize
	 * @return productExecution 封装了状态 类型 数据的对象
	 */
	productExecution getProductList(Product productCondition, int pageIndex, int pageSize);
}
