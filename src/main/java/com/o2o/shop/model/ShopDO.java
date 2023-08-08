package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商铺实体类
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_shop")
@JsonIgnoreProperties({"version","deleted"})
public class ShopDO {

    /**
     * 商铺Id
     */
    @TableId(value = "shop_id", type = IdType.AUTO)
    private Integer shopId;

    /**
     * 店铺创建人
     */
    @TableField("owner_id")
    private Integer ownerId;

    /**
     * 所属区域Id
     */
    @TableField("area_id")
    private Integer areaId;

    /**
     * 商铺类别Id
     */
    @TableField("shop_category_id")
    private Integer shopCategoryId;

    /**
     * 商铺名称
     */
    @TableField("shop_name")
    private String shopName;

    /**
     * 商铺描述
     */
    @TableField("shop_desc")
    private String shopDesc;

    /**
     * 商铺地址
     */
    @TableField("shop_addr")
    private String shopAddr;

    /**
     * 联系电话
     */
    @TableField("phone")
    private String phone;

    /**
     * 商铺图片
     */
    @TableField("shop_img")
    private String shopImg;

    /**
     * 商铺权重
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
     * 启用状态
     */
    @TableField("enable_status")
    private Integer enableStatus;

    /**
     * 审核意见
     */
    @TableField("advice")
    private String advice;

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
