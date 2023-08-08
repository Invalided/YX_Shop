package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品表
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_product")
@JsonIgnoreProperties({"version","deleted"})
public class ProductDO {

    /**
     * 商品Id
     */
    @TableId(value = "product_id")
    private Integer productId;

    /**
     * 商品名称
     */
    @TableField("product_name")
    private String productName;

    /**
     * 商品描述
     */
    @TableField("product_desc")
    private String productDesc;

    /**
     * 商品图片地址,存放主要缩略图
     */
    @TableField("img_addr")
    private String imgAddr;

    /**
     * 普通价格
     */
    @TableField("normal_price")
    private String normalPrice;

    /**
     * 促销价格
     */
    @TableField("promotion_price")
    private String promotionPrice;

    /**
     * 商品权重
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 商品购买可获积分
     */
    @TableField("point")
    private Integer point;
    /**
     * 创建日期
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "last_edit_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 启用状态
     */
    @TableField("enable_status")
    private Integer enableStatus;

    /**
     * 商品类别Id
     */
    @TableField("product_category_id")
    private Integer productCategoryId;

    /**
     * 所属店铺Id
     */
    @TableField("shop_id")
    private Integer shopId;

    /**
     * 版本号,用于乐观锁实现
     */
    @TableField("version")
    @Version
    private Integer version;

    /**
     * 逻辑删除:0.未删除 1.已删除
     */
    @TableField("deleted")
    @TableLogic
    private Integer deleted;
}
