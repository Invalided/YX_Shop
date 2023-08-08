package com.o2o.shop.vo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.o2o.shop.model.AreaDO;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ShopCategoryDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * 商铺返回VO对象
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShopVO {

    /**
     * 商铺Id
     */
    private Integer shopId;

    /**
     * 所属者Id
     */
    private Integer ownerId;
    /**
     * 商铺名称
     */
    private String shopName;

    /**
     * 商铺描述
     */
    private String shopDesc;

    /**
     * 商铺地址
     */
    private String shopAddress;

    /**
     * 联系电话
     */
    private String phone;

    /**
     * 商铺图片，可不返回
     */
    private String shopImg;

    /**
     * 商铺权重
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

    /**
     * 启用状态
     */
    private Integer enableStatus;

    /**
     * 审核意见
     */
    private String advice;

    /**
     * 商铺所属类别名称,使用类别对象,用于映射
     */
    private ShopCategoryDO shopCategory;

    /**
     * 所属区域
     */
    private AreaDO area;

    /**
     * 所属负责人
     */
    @JsonIgnore
    private PersonInfoDO ownerInfo;
}
