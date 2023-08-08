package com.o2o.shop.service;

import com.o2o.shop.dto.mall.ProductCategoryDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;

import java.util.List;

/**
 * 商品类别信息
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface ProductCategoryService{
    /**
     * 根据商铺id查询其所有商品类别信息
     * @param shopId 商铺id
     * @return
     */
    PageVO queryProductCategoryById(Integer shopId);

    /**
     * 批量新增商品类别信息
     * @param productCategoryDTOList 商品类别列表
     * @return
     */
    ResultDataVO batchInsertProductCategory(List<ProductCategoryDTO> productCategoryDTOList);

    /**
     * 删除指定商铺下的指定的类别信息
     * @param dto 商品类别dto对象
     * @return
     */
    ResultDataVO deleteProductCategoryById(ProductCategoryDTO dto);
}
