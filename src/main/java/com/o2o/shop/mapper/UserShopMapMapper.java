package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.UserShopBO;
import com.o2o.shop.model.UserShopMapDO;
import com.o2o.shop.vo.UserProductMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户积分与店铺映射
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface UserShopMapMapper extends BaseMapper<UserShopMapDO> {

    /**
     * 多表联查,实现分页操作,复用MP的selectPage方法
     * @param page 分页参数
     * @Param(Constants.WRAPPER)QueryWrapper queryWrapper,用于wrapper条件参数绑定，本例中去除
     * @param userShopBO 联查条件
     * @return
     */
    Page<UserProductMapVO> queryShopMapPointPage(Page<UserShopMapDO> page,
                                            @Param("userShopCondition") UserShopBO userShopBO);
}
