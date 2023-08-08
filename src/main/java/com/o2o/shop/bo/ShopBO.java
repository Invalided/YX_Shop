package com.o2o.shop.bo;

import com.o2o.shop.model.AreaDO;
import com.o2o.shop.model.PersonInfoDO;
import lombok.Data;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-07-23:48
 * 商铺类的BO对象，由于单纯的DO对象无法满足需求，故新增此类
 */
@Data
public class ShopBO {
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
    private ShopCategoryBO shopCategoryBO;

    /**
     * 所属区域
     */
    private AreaDO areaDO;

    /**
     * 所属负责人
     */
    private PersonInfoDO ownerInfo;
}
