package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.ShopBO;
import com.o2o.shop.model.ShopDO;
import com.o2o.shop.vo.ShopVO;
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
public interface ShopMapper extends BaseMapper<ShopDO> {

    /**
     * 自定义Shop查询语句
     * @return
     */
    //List<ShopVO> queryProductOrderPage();

    /**
     * 通过id查询商铺信息，多表联查
     * @param shopId
     * @return
     */
    ShopVO queryByShopId(Integer shopId);


    /**
     * 多表联查,实现分页操作,复用MP的selectPage方法
     * @param page
     * @Param(Constants.WRAPPER)QueryWrapper queryWrapper,用于wrapper条件参数绑定，本例中去除
     * @param shopCondition
     * @return
     */
    Page<ShopVO> queryShopListPage(Page<ShopDO> page ,
                                   @Param("shopCondition") ShopBO shopCondition);

}
