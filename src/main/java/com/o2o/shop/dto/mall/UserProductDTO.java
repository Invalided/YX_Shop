package com.o2o.shop.dto.mall;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;
import java.time.Instant;

/**
 * @author 勿忘初心
 * @since 2023-07-15-23:01
 * 用户订单DTO对象,条件查询
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserProductDTO {
    /**
     * 空属性判断对象
     */
    private static final UserConsumptionDTO EMPTY = new UserConsumptionDTO();

    /**
     * 用户id
     */
    @Positive(message = "用户id不合法")
    private Integer userId;

    /**
     * 商铺id
     */
    @Positive(message = "商铺id不合法")
    private Integer ShopId;

    /**
     * 用户名称,用于条件查询
     */
    private String userName;

    /**
     * 商品名称, 用于条件查询
     */
    private String productName;

    /**
     * 创建时间,用于查询指定日期的订单信息,使用了时间戳,单位 s
     */
    @Pattern(regexp = "\\d+",message = "时间格式错误")
    private Instant createTime;

    public boolean isEmpty(){
        return this.equals(EMPTY);
    }
}

