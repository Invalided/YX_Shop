package com.o2o.shop.dto.mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-15-23:01
 * 用户消费DTO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserConsumptionDTO {
    /**
     * 用户id
     */
    @NotNull(message = "用户id不能为空")
    @Positive(message = "用户id不合法")
    private Integer userId;

    /**
     * 商铺id
     */
    @NotNull(message = "商铺id不能为空")
    @Positive(message = "商铺id不合法")
    private Integer ShopId;

    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空")
    @Positive(message = "商品id不合法")
    private Integer ProductId;

    /**
     * 购买的商品数量
     */
    @NotNull(message = "商品数量不能为空")
    @Positive(message = "商品数量不合法")
    private Integer nums;

}

