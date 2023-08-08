package com.o2o.shop.api.v1.superadmin;

import com.o2o.shop.dto.superadmin.ShopDTO;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import javax.validation.groups.Default;

/**
 * ShopController 商铺信息管理(Admin)
 * @author 勿忘初心
 * @since 2023-07-08-16:38
 */
@RestController
@Slf4j
@RequestMapping("/admin")
@Validated
public class ShopController {

    @Autowired
    private ShopService shopService;

    /**
     * 获取所有商铺信息
     * @param pageNum 当前页码
     * @param pageSize 页码数据量
     * @param shopDTO shop数据传输对象,也可定义多个参数在此处拼接
     * 该接口可以实现按条件查询的操作，自行传入相关参数即可
     * @return
     */
    @GetMapping("/shop/all")
    PageVO queryShopList(ShopDTO shopDTO,
                         @Range(min = 1,max = 20,message = "参数错误") Integer pageNum,
                         @Range(min = -1,max = 20,message = "参数错误") Integer pageSize){
         return shopService.queryShopListPage(pageNum, pageSize, shopDTO);
    }

    /**
     * 根据id获取指定店铺信息
     * @param id 商铺id
     * @return
     */
    @GetMapping("/shop/{id}")
    ShopVO queryShopById(@NotNull(message = "商铺id不能为空")
                         @Positive(message = "商铺id不合法")
                         @PathVariable Integer id){
        return shopService.queryShopById(id);
    }

    /**
     * 更新商铺信息
     * @param shopDTO 商铺DTO
     * @return
     */
    @PutMapping("/shop/update")
    ResultDataVO updateShop(@RequestBody @Validated({UpdateGroup.class, Default.class}) ShopDTO shopDTO){
        return shopService.updateShop(shopDTO);
    }

}
