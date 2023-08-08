package com.o2o.shop.bo;

import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ShopDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

/**
 * @author 勿忘初心
 * @since 2023-07-24-12:31
 * 用户积分查询BO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserShopBO {
    /**
     * 商铺信息
     */
    private ShopDO shop;

    /**
     * 用户信息
     */
    private PersonInfoDO user;

    /**
     * 积分创建时间
     */
    private Instant createTime;
}
