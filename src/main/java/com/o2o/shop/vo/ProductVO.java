package com.o2o.shop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.o2o.shop.model.ProductImgDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-07-15-20:08
 * 商品VO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductVO {
    /**
     * 商品id
     */
    private Long productId;
    /**
     * 商品id
     */
    private String productName;
    /**
     * 商品描述
     */
    private String productDesc;
    /**
     * 缩略图
     */
    private String imgAddr;
    /**
     * 缩略图
     */
    private String normalPrice;
    /**
     * 促销价
     */
    private String promotionPrice;
    /**
     * 权重
     */
    private Integer priority;
    /**
     * 商品积分
     */
    private Integer point;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date lastEditTime;
    /**
     * 0.下架 1.在前端系统展示
     */
    private Integer enableStatus;
    /**
     * 商品详情图片列表 商品与详情图为一对多关系
     */
    private List<ProductImgDO> productImgList;
    /**
     * 商品类别
     */
    private ProductCategoryVO productCategory;
    /**
     * 商品所属店铺
     */
    private ShopVO shop;
}
