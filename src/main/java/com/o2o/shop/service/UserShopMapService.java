package com.o2o.shop.service;

import com.o2o.shop.dto.shopmanager.ManagePointDTO;
import com.o2o.shop.model.UserShopMapDO;
import com.o2o.shop.vo.PageVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-17-17:52
 * 用户积分店铺映射
 */
@Validated
public interface UserShopMapService {

    /**
     * 根据用户id和商铺id获取该店铺的积分信息
     * @param userId
     * @param ShopId
     * @return
     */
    UserShopMapDO queryPointInfoById(Integer userId,Integer ShopId);

    /**
     * 根据用户条件进行查询
     * @param managePointDTO 积分DTO
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    PageVO queryUserPointListByCondition(@NotNull(message = "参数不能为空") ManagePointDTO managePointDTO, Integer pageNum,
                                         Integer pageSize);

    /**
     * 根据实体对象更新积分信息
     * @param userShopMapDO
     * @return
     */
    int updatePointInfoByEntity(UserShopMapDO userShopMapDO);

}
