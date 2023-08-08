package com.o2o.shop.bo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author 勿忘初心
 * @since 2023-07-08-23:36
 * 封装类别信息BO对象
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ShopCategoryBO {

    /**
     * 构建空属性对象
     */
    private static final ShopCategoryBO EMPTY = new ShopCategoryBO();
    /**
     * 商铺类别Id
     */
    private Integer shopCategoryId;

    /**
     * 商铺类别名称
     */
    private String shopCategoryName;

    /**
     * 商铺类别描述
     */
    private String shopCategoryDesc;

    /**
     * 商铺类别图片
     */
    private String shopCategoryImg;

    /**
     * 权重
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 修改时间
     */
    private Date updateTime;

    /**
     * 封装父类信息对象
     */
    private ShopCategoryBO parent;

    /**
     * 判断对象是否未空属性
     * @return
     */
    public boolean isEmpty(){
        return this.equals(EMPTY);
    }
}
