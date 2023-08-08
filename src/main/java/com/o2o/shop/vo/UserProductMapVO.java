package com.o2o.shop.vo;

import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ProductDO;
import com.o2o.shop.model.ShopDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-17-23:41
 * 用户商品订单映射VO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductMapVO {
    /**
     * 商品订单id
     */
    private Integer userProductId;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 消费商品所获得的积分
     */
    private Integer point;
    /**
     * 顾客信息实体
     */
    private PersonInfoDO user;
    /**
     * 商品信息实体类
     */
    private ProductDO product;
    /**
     * 商铺信息实体类
     */
    private ShopDO shop;
    /**
     * 工作人员信息实体类
     */
    private PersonInfoDO operator;
    /**
     * 消费商品数量
     */
    private Integer nums;

}
