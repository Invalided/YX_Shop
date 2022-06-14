package com.study.o2o.dao;

import java.util.List;


import com.study.o2o.entity.productImg;

public interface productImgDao {
	/**
	 * 批量添加商品详情图片
	 * @param productImgList
	 * @return
	 */
	 int batchInsertProductImg(List<productImg> productImgList);
	/**
	 * 根据商品id删除对应的图片
	 * @param productId
	 * @return
	 */
	int deleteProductImgByProductId(long productId);
	/**
	 * 列出某个商品的详情图列表
	 * 
	 * @param productId
	 * @return
	 */
	List<productImg> queryProductImgList(long productId);

}
