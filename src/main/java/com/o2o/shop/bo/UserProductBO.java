package com.o2o.shop.bo;

import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ProductDO;
import com.o2o.shop.model.ShopDO;
import lombok.Data;

import java.time.Instant;

/**
 * @author 勿忘初心
 * @since 2023-07-18-0:14
 * 用户商品订单信息BO对象
 */
@Data
public class UserProductBO {

    /**
     * 用户信息
     */
    private PersonInfoDO user;

    /**
     * 商铺信息
     */
    private ShopDO shop;

    /**
     * 商品信息
     */
    private ProductDO product;

    /**
     * 创建时间,Instant相比于Date性能更好
     */
    private Instant createTime;
}
