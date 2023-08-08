package com.o2o.shop.dto.mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author 勿忘初心
 * @since 2023-07-16-14:28
 * 用户消费商品DTO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ConsumptionProductDTO {

    /**
     *  消费商品id
     */
    @NotNull(message = "商品id不能为空")
    private Integer productId;

    /**
     * 消费商品name;
     */
    @NotBlank(message = "商品名称不能为空")
    private Integer productName;

    /**
     * 消费商品的价格
     */
    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    /**
     * 消费商品的总数
     */
    @NotNull(message = "商品的总数不能为空")
    private Integer productCount;
}
