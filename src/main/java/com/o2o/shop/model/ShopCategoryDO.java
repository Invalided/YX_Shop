package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商铺类别实体类
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_shop_category")
@JsonIgnoreProperties({"version","deleted"})
public class ShopCategoryDO {

    /**
     * 商铺类别Id
     */
    @TableId(value = "shop_category_id")
    private Integer shopCategoryId;

    /**
     * 商铺类别名称
     */
    @TableField("shop_category_name")
    private String shopCategoryName;

    /**
     * 商铺类别描述
     */
    @TableField("shop_category_desc")
    private String shopCategoryDesc;

    /**
     * 商铺类别图片
     */
    @TableField("shop_category_img")
    private String shopCategoryImg;

    /**
     * 权重
     */
    @TableField("priority")
    private Integer priority;

    /**
     * 创建时间
     */
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 修改时间
     */
    @TableField(value = "last_edit_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    /**
     * 父类Id
     */
    @TableField("parent_id")
    private Integer parentId;

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
