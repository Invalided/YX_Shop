package com.study.o2o.service; 

import java.util.List;

import com.study.o2o.dto.ShopCategoryExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.shopCategory;

public interface shopCategoryService {
	//定义redis中的key前缀
	public static final String SCLISTKEY = "shopcategorylist";
	/**
	 * 根据查询条件获取ShopCategory列表
	 * @param shopCategoryCondition
	 * @return
	 */
	List<shopCategory> getShopCategoryList (shopCategory shopCategoryCondition);
	
	/**
	 * 添加店铺类别, 并存储店铺类别图片
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 * @return
	 */
	ShopCategoryExecution addShopCategory(shopCategory shopCategory,imageHolder thumbnail);
	
	/**
	 * 修改店铺类别
	 * 
	 * @param shopCategory
	 * @param thumbnail
	 * @return
	 */
	ShopCategoryExecution modifyShopCategory(shopCategory shopCategory,imageHolder thumbnail);
	
	/**
	 * 根据Id返回店铺类别信息
	 * 
	 * @param shopCategoryId
	 * @return
	 */
	shopCategory getShopCategoryById(Long shopCategoryId);
	
	/**
	 * 根据Id删除店铺类别
	 * @param shopCategoryId
	 * @return
	 */
	ShopCategoryExecution removeShopCategoryById(long shopCategoryId);
}
