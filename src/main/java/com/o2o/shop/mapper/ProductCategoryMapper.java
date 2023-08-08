package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.o2o.shop.model.ProductCategoryDO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface ProductCategoryMapper extends BaseMapper<ProductCategoryDO> {
    /**
     * 批量新增类别
     * @param productCategoryDTOList
     * @return
     */
    int batchInsertProductCategory(@Param("productCategoryList") List<ProductCategoryDO> productCategoryDTOList);
}
