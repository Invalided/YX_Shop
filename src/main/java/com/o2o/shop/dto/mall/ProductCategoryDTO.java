package com.o2o.shop.dto.mall;

import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-15-17:59
 * 商品类别DTO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProductCategoryDTO {

    /**
     * 商品类别id
     */
    @NotNull(message = "商品类别id不能为空",groups = {UpdateGroup.class})
    @Range(min = 1,max = 3000, message = "类别id不合法",groups = {UpdateGroup.class})
    private Integer productCategoryId;

    /**
     * 所属商铺id
     */
    @Range(min = 1,max = 5000,message = "商铺id不合法",groups = {UpdateGroup.class})
    private Integer shopId;

    /**
     * 商品类别名称
     */
    @NotBlank(message = "商品类别名称不能为空")
    @Length(max = 5,message = "类别名称最多5个字")
    private String productCategoryName;

    /**
     * 权重
     */
    @NotNull(message = "权重值不能为空")
    @Range(min = 1,max = 100,message = "权重参数不合法")
    private Integer priority;
}
