package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 用户购买商品映射
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_user_product_map")
@JsonIgnoreProperties({"version","deleted"})
public class UserProductMapDO {
    /**
     * 用户商品Id
     */
    @TableId(value = "user_product_id")
    private Integer userProductId;

    /**
     * 用户Id
     */
    @TableField("user_id")
    private Integer userId;

    /**
     * 商品Id
     */
    @TableField("product_id")
    private Integer productId;

    /**
     * 商铺Id
     */
    @TableField("shop_id")
    private Integer shopId;

    /**
     * 操作者Id
     */
    @TableField("operator_id")
    private Integer operatorId;

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
     * 积分
     */
    @TableField("point")
    private Integer point;

    /**
     * 数量
     */
    @TableField("nums")
    private Integer nums;

    /**
     * 购买时单价
     */
    @TableField("trade_price")
    private String tradePrice;

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
