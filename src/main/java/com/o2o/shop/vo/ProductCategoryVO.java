package com.o2o.shop.vo;

import lombok.Data;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-15-17:47
 * 商品类别VO类
 */
@Data
public class ProductCategoryVO {
    /**
     * 商品类别id
     */
    private Integer productCategoryId;
    /**
     * 所属商铺id
     */
    private Integer shopId;
    /**
     * 商品类别名称
     */
    private String productCategoryName;
    /**
     * 权重
     */
    private Integer priority;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 修改时间
     */
    private Date updateTime;
}
