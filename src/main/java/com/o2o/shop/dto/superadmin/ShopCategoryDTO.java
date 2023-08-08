package com.o2o.shop.dto.superadmin;

import com.o2o.shop.validator.area.UpdateGroup;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

/**
 * @author 勿忘初心
 * @since 2023-07-07-23:48
 * 商铺类的DTO对象
 */
@Data
public class ShopCategoryDTO {

    /**
     * 构建一个空对象用于判空
     */
    private static final ShopCategoryDTO EMPTY = new ShopCategoryDTO();

    /**
     * 商铺类别Id
     */
    @NotNull(message = "类别id不能为空",groups = UpdateGroup.class)
    private Integer shopCategoryId;

    /**
     * 商铺类别名称
     */
    @NotBlank(message = "类别名称不能为空")
    @Length(max = 5,message = "类别名称最多5个字符")
    private String shopCategoryName;

    /**
     * 商铺类别描述
     */
    @NotBlank(message = "类别描述不能为空")
    @Length(max = 10,message = "类别描述最多10个字符")
    private String shopCategoryDesc;

    /**
     * 商铺类别图片存储路径
     */
    private String shopCategoryImg;

    /**
     * 权重
     */
    @NotNull(message = "权重不能为空")
    private Integer priority;

    /**
     * 封装父类信息对象
     */
    private ShopCategoryDTO parent;

    /**
     * 所属父类id
     * integer 整数部分的位数
     * fraction 小数部分的位数
     */
    @Digits(integer = 5, fraction = 0, message = "一级类别错误")
    private Integer parentId;

    /**
     * 提供空对象判断
     * @return
     */
    public boolean isEmpty(){
        return this.equals(EMPTY);
    }
}
