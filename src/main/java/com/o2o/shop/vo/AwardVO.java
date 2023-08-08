package com.o2o.shop.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-23-13:37
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AwardVO {

    /**
     * 奖品id
     */
    private Integer awardId;
    /**
     * 所属店铺id
     */
    private Integer shopId;
    /**
     * 奖品名称
     */
    private String awardName;
    /**
     * 奖品描述
     */
    private String awardDesc;
    /**
     * 奖品图片地址
     */
    private String awardImg;
    /**
     * 兑换所需积分
     */
    private Integer point;
    /**
     * 权重, 越大排名越前
     */
    private Integer priority;
    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 可用状态 0.禁用 1.可用
     */
    private Integer enableStatus;
}
