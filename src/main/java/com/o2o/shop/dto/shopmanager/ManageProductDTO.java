package com.o2o.shop.dto.shopmanager;

import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-20-18:28
 * 商品管理
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageProductDTO {

    /**
     * 所属商铺id,可从session中获取
     */
    private Integer shopId;
    /**
     * 商品id
     */
    @NotNull(message = "商品id不能为空",groups = {UpdateGroup.class})
    @Range(min = 1,max = 1000,message = "商品id不合法",groups = {UpdateGroup.class})
    private Integer productId;
    /**
     * 商品名称
     */
    @NotBlank(message = "商品名不能为空",groups = {AddGroup.class})
    private String productName;

    /**
     * 商品类别
     */
    @NotNull(message = "商品类别不能为空",groups = {AddGroup.class})
    private Integer productCategoryId;

    /**
     * 商品权重
     */
    @Positive(message = "权重参数不合法")
    private Integer priority;

    /**
     * 商品积分
     */
    @Positive(message = "积分参数不合法")
    private Integer point;

    /**
     * 商品日常价格(原价)
     */
    @NotNull(message = "商品价格不能为空",groups = {AddGroup.class})
    private String normalPrice;

    /**
     * 商品促销价格(现价)
     */
    private String promotionPrice;

    /**
     * 商品描述
     */
    private String productDesc;

    /**
     * 启用状态
     */
    @Digits(message = "商品参数不合法", integer = 1, fraction = 0)
    private Integer enableStatus;
}
