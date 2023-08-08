package com.o2o.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.HeadLineBO;
import com.o2o.shop.model.HeadLineDO;
import com.o2o.shop.vo.HeadLineVO;
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
public interface HeadLineMapper extends BaseMapper<HeadLineDO> {

    /**
     * 自定义条件查询，复用MP的Page分页
     * @param page 分页对象
     * @param headLineBO 分页条件
     * @return
     */
    Page<HeadLineVO> queryHeadLinePage(Page<HeadLineDO> page,
                                         @Param("headLineCondition")HeadLineBO headLineBO);
}
