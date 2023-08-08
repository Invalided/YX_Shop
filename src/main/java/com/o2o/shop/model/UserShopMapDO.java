package com.o2o.shop.model;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商铺积分映射
 * @author 勿忘初心
 * @since 2023-07-17-17:54
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@TableName("tb_user_shop_map")
@JsonIgnoreProperties({"version","deleted"})
public class UserShopMapDO {

    /**
     * 主键id
     */
    @TableId(value = "user_shop_id")
    private Integer userShopId;

    /**
     * 用户id
     */
    @TableField(value = "user_id")
    private Integer userId;

    /**
     * 商铺id
     */
    @TableField(value = "shop_id")
    private Integer shopId;

    /**
     * 店铺累计积分
     */
    @TableField(value = "point")
    private Integer point;

    /**
     * 创建时间
     */
    @TableField(value = "create_time",fill = FieldFill.INSERT)
    private Date createTime;

    /**
     * 更新时间
     */
    @TableField(value = "update_time",fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

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
