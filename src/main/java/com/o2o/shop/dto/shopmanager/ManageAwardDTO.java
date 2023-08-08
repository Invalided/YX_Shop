package com.o2o.shop.dto.shopmanager;

import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-23-13:11
 * 奖品管理DTO
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageAwardDTO {

    /**
     * 奖品id
     */
    @NotNull(message = "奖品id不能为空",groups = {UpdateGroup.class})
    @Range(min = 1,max = 5000, message = "奖品id不合法")
    private Integer awardId;
    /**
     * 所属店铺id
     */
    @Range(min = 1,max = 5000, message = "商铺id不合法")
    private Integer shopId;
    /**
     * 奖品名称
     */
    @NotNull(message = "奖品名称不能为空",groups = {AddGroup.class})
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
    @NotNull(message = "奖品积分不能为空",groups = {AddGroup.class})
    @Range(min = 1,max = 50000,message = "奖品积分不合法")
    private Integer point;
    /**
     * 权重, 越大排名越前
     */
    private Integer priority;

    /**
     * 可用状态 0.禁用 1.可用
     */
    private Integer enableStatus;
    
}
