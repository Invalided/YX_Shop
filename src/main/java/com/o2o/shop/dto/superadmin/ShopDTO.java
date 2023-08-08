package com.o2o.shop.dto.superadmin;

import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-08-15:50
 * 商铺信息传输对象
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShopDTO {
    /**
     * 商铺Id
     */
    @NotNull(message = "id不能为空",groups = {UpdateGroup.class})
    @Positive(message = "商铺id参数不合法",groups = {UpdateGroup.class})
    private Integer shopId;

    /**
     * 商铺名称,可用于条件查询
     */
    private String shopName;

    /**
     * 区域Id,可用于条件查询
     */
    private Integer areaId;
    /**
     * 商铺权重
     */
    @Range(min = 1,max = 10000,message = "权重参数不合法")
    private Integer priority;

    /**
     * 启用状态,可用于条件查询
     */
    private Integer enableStatus;

    /**
     * 负责人Id,可用于条件查询
     */
    private Integer ownerId;

    /**
     * 审核意见
     */
    private String advice;

    /**
     * 一级类别Id，用于条件查询
     */
    @Range(min = 1,max = 200,message = "类别参数错误")
    private Integer parentId;

    /**
     * 二级类别Id，用于条件查询
     */
    @Range(min = 1,max = 200,message = "类别参数错误")
    private Integer shopCategoryId;
}
