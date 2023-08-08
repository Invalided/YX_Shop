package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商品详情图
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_product_img")
@JsonIgnoreProperties({"version","deleted"})
public class ProductImgDO {

    /**
     * 商品图片Id
     */
    @TableId(value = "product_img_id")
    private Integer productImgId;

    /**
     * 图片地址
     */
    @TableField("img_addr")
    private String imgAddr;

    /**
     * 图片描述
     */
    @TableField("img_desc")
    private String imgDesc;

    /**
     * 图片权重
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "last_edit_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;


    /**
     * 所属商品Id
     */
    @TableField("product_id")
    private Integer productId;

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
