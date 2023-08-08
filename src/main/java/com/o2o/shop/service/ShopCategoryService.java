package com.o2o.shop.service;

import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.dto.superadmin.ShopCategoryDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface ShopCategoryService {

    /**
     * 商铺类别key
     */
    String SHOP_CATEGORY = "shop_category";

    /**
     * 获取商铺类别信息列表
     * @param pageNum 当前页码
     * @param pageSize 页码数据量
     * @param shopCategoryCondition
     * @param allPage
     * @return
     */
    PageVO queryAllShopCategoryListPage(Integer pageNum, Integer pageSize,
                                        ShopCategoryBO shopCategoryCondition,
                                        boolean allPage);

    /**
     * 创建一个新的类别信息
     * @param shopCategoryDTO 商铺类别传输对象
     * @param multipartFile 图片文件
     * @return
     */
    ResultDataVO createShopCategory(ShopCategoryDTO shopCategoryDTO, MultipartFile multipartFile);

    /**
     * 更新类别信息
     * @param shopCategoryDTO 商铺类别传输对象
     * @param multipartFile 图片文件,可选
     * @return
     */
    ResultDataVO updateShopCategory(ShopCategoryDTO shopCategoryDTO, MultipartFile multipartFile);

    /**
     * 删除类别信息
     * @param id 类别id
     * @return
     */
    ResultDataVO deleteShopCategoryById(Integer id);
}
