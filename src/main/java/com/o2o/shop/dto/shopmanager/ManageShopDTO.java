package com.o2o.shop.dto.shopmanager;

import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

/**
 * @author 勿忘初心
 * @since 2023-07-18-20:57
 * 商铺DTO对象(Manger)
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManageShopDTO {

    /**
     * 商铺id
     */
    @NotNull(message = "商铺id不能为空",groups = {UpdateGroup.class})
    @Positive(message = "商铺id不合法")
    private Integer shopId;

    /**
     * 创建者id
     */
    private Integer ownerId;
    /**
     * 商铺名称,创建时需使用
     */
    @NotBlank(message = "商铺名不能为空",groups = {AddGroup.class})
    private String shopName;

    /**
     * 类别Id,商铺创建可用
     */
    @NotNull(message = "类别id不能为空",groups = {AddGroup.class})
    @Range(min = 1,max = 200,message = "类别参数错误",groups = {AddGroup.class})
    private Integer shopCategoryId;

    /**
     * 区域Id,可用于条件查询
     */
    @NotNull(message = "区域id不能为空",groups = {AddGroup.class})
    @Range(min = 1,max = 200,message = "区域参数错误",groups = {AddGroup.class})
    private Integer areaId;

    /**
     * 商铺地址
     */
    @NotNull(message = "商铺地址不能为空",groups = {AddGroup.class})
    private String shopAddr;

    /**
     * 联系电话
     */
    @NotBlank(message = "联系电话不能为空",groups = {AddGroup.class})
    @Pattern(regexp = "^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\\d{8}$",
            message = "手机号码格式不正确",groups = {AddGroup.class,UpdateGroup.class})
    private String phone;

    /**
     * 商铺简介
     */
    private String shopDesc;

}
