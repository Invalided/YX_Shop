package com.o2o.shop.service;

import com.o2o.shop.dto.shopmanager.ManageShopDTO;
import com.o2o.shop.dto.superadmin.ShopDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopVO;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface ShopService{

    /**
     * 获取所有的商铺信息列表
     * @param pageNum 页码
     * @param pageSize 页面数据量
     * @param shopDTO 商铺传输对象
     * @return
     */
    PageVO queryShopListPage(Integer pageNum, Integer pageSize,
                             ShopDTO shopDTO);

    /**
     * 根据id获取商铺信息
     * @param id 商铺id
     * @return
     */
    ShopVO queryShopById(Integer id);

    /**
     * 修改商铺信息,简单信息修改,用于Admin
     * @param shopDTO 商铺DTO对象
     * @return
     */
    ResultDataVO updateShop(ShopDTO shopDTO);


    /**
     * 创建商铺
     * @param managerShopDTO 商铺管理DTO
     * @param multipartFile 商铺缩略图
     * @return
     */
    ResultDataVO createShop(ManageShopDTO managerShopDTO, MultipartFile multipartFile);

    /**
     * 更新商铺信息，用于Manager
     * @param managerShopDTO 商家DTO对象
     * @param multipartFile 商铺图片
     * @return
     */
    ResultDataVO updateShop(ManageShopDTO managerShopDTO, MultipartFile multipartFile);

    /**
     * 删除店铺
     * @param managerShopDTO 入参 shopId,userId
     * @return
     */
    ResultDataVO deleteShop(ManageShopDTO managerShopDTO);

    /**
     * 主要做判断处理,防止用户横向越权
     * @param shopId 商铺id,可为空。
     * @return
     */
    ResultDataVO shopManagementInfo(Integer shopId);
}
