package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.UserProductBO;
import com.o2o.shop.model.UserProductMapDO;
import com.o2o.shop.vo.UserProductMapVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 用户购买商品订单记录
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Mapper
public interface UserProductMapMapper extends BaseMapper<UserProductMapDO> {

    /**
     * 多表联查,实现分页操作,复用MP的selectPage方法
     * @param page
     * @Param(Constants.WRAPPER)QueryWrapper queryWrapper,用于wrapper条件参数绑定，本例中去除
     * @param userProductCondition 封装查询条件
     * @return
     */
    Page<UserProductMapVO> queryProductOrderPage(Page<UserProductMapDO> page ,
                                                 @Param("userProductCondition") UserProductBO userProductCondition);
}
