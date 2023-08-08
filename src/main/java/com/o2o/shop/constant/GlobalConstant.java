package com.o2o.shop.constant;

/**
 * @author 勿忘初心
 * @since 2023-07-26-12:01
 * 创建常量，减少重复创建对象带来的内存占用
 */
public class GlobalConstant {
    /**
     * 用户session信息
     */
    public static final String USER_SESSION_INFO = "authInfo";
    /**
     * 商家所拥有的店铺信息(存于Session)
     */
    public static final String MANAGER_CONTAIN_SHOP = "mallCollection";
    /**
     * 商家目前所操作的的店铺信息
     */
    public static final String CURRENT_SHOP = "currentMall";
    /**
     * 商铺图片存储名称
     */
    public static final String MALL_IMAGE = "mall";
    /**
     * 商铺类别图片存储名称
     */
    public static final String SHOP_CATEGORY_IMAGE = "shopCategory";
    /**
     * 头条图片存储名称
     */
    public static final String HEADLINE_IMAGE = "headLine";


    /**
     * 用于实现用户类型的标识
     */
    public enum RoleType{
        // 用户类型
        USER(1),
        // 商家类型
        MANAGER(2),
        // 管理员类型
        ADMIN(3);

        private final int value;

        RoleType(int value){
            this.value = value;
        }

        public int getValue(){
            return value;
        }
    }
}
