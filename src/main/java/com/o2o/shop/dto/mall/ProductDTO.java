package com.o2o.shop.dto.mall;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-15-20:22
 * 商品DTO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductDTO {
    /**
     * 商品名
     */
    private String productName;

    /**
     * 商品状态
     */
    @Positive(message = "状态参数错误")
    private Integer enableStatus;

    /**
     * 所属商铺id
     */
    @NotNull(message = "商铺id不能为空")
    @Range(min = 1,max=1000,message = "商铺Id错误")
    private Integer shopId;

    /**
     * 商品类别id
     */
    @Range(min = 1,max = 1000,message = "商品类别id错误")
    private Integer productCategoryId;
}
