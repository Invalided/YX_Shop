package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.shopmanager.ManageShopDTO;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.ResultDataVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ShopInfoManageController 商铺信息管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-21-16:03
 * 对应商铺的详情管理页面,主要处理用户对商铺的访问,防止横向越权
 * 以及实现商铺信息管理
 */
@RestController
@Validated
@RequestMapping("/manager")
public class ShopInfoManageController {

    @Autowired
    private ShopService shopService;

    /**
     * 根据商铺id获取当前商铺信息
     * @param shopId 商铺id
     * @return
     */
    @GetMapping("/{shopId}")
    ResultDataVO shopManagementInfo(@PathVariable(required = false)
                                    @Range(min = 1,max = 5000, message = "商铺id不合法") Integer shopId){
        return shopService.shopManagementInfo(shopId);
    }

    /**
     * 商铺信息修改
     * @param manageShopDTO 商铺管理对象
     * @param thumbnail 商铺缩略图
     * @return
     */
    @PutMapping("/shop_edit")
    ResultDataVO shopInfoModify(@Validated(UpdateGroup.class) ManageShopDTO manageShopDTO,
                                MultipartFile thumbnail){
        return shopService.updateShop(manageShopDTO,thumbnail);
    }
}
