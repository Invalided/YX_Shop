package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.model.ShopCategoryDO;
import com.o2o.shop.vo.ShopCategoryVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface ShopCategoryMapper extends BaseMapper<ShopCategoryDO> {

    /**
     * 类别信息查询自定义语句,分页,联查
     * @param page 分页对象
     * @param shopCategoryCondition 查询条件
     * @Param allPage 指定是否加载所有分类
     * @return
     */
    Page<ShopCategoryVO> queryShopCategoryListPage(Page<ShopCategoryDO> page,
                                                   @Param("shopCategoryCondition") ShopCategoryBO shopCategoryCondition,
                                                   @Param("allPage") Boolean allPage);
}
