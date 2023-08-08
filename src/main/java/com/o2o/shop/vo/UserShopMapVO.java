package com.o2o.shop.vo;

import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ShopDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-17-17:54
 * 用户商铺积分映射VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShopMapVO {
    /**
     * 用户商铺映射Id
     */
    private Integer userShopId;

    /**
     * 用户id
     */
    private Integer userId;

    /**
     * 商铺id
     */
    private Integer shopId;

    /**
     * 商品id
     */
    private Integer productId;
    /**
     * 店铺累计积分
     */
    private Integer point;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 更新时间
     */
    private Date updateTime;

    /**
     * 用户信息
     */
    private PersonInfoDO user;

    /**
     * 店铺信息
     */
    private ShopDO shop;
}
