package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.o2o.shop.model.ProductDO;
import com.o2o.shop.vo.ProductVO;
import org.apache.ibatis.annotations.Mapper;

/**
 * <p>
 *  ProductMapper 接口
 * </p>
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface ProductMapper extends BaseMapper<ProductDO> {
    /**
     * 根据id获取Product详细信息，联查
     * @param productId
     * @return
     */
    ProductVO queryProductById(Integer productId);
}
