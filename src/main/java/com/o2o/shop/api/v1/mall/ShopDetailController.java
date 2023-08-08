package com.o2o.shop.api.v1.mall;

import com.o2o.shop.dto.mall.ProductDTO;
import com.o2o.shop.service.ProductCategoryService;
import com.o2o.shop.service.ProductService;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;

/**
 * ShopDetailController 商铺详情
 * @author 勿忘初心
 * @since 2023-07-15-17:36
 */
@RequestMapping("/mall")
@RestController
@Validated
public class ShopDetailController {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据商铺Id获取商铺信息
     * @param shopId
     * @return
     */
    @GetMapping("/{id}")
    ResultDataVO listShopDetailPageInfo(@NotNull(message = "商铺Id不能为空")
                                        @Positive(message = "参数不合法")
                                        @PathVariable(name = "id") Integer shopId){
        // 获取当前shop信息
        ShopVO shopInfo = shopService.queryShopById(shopId);
        // 获取该商铺下的所有商品分类信息
        PageVO productCategoryDOList = productCategoryService.queryProductCategoryById(shopId);
        // 封装数据
        Map<String,Object> shopDetail = new HashMap<>(2);
        shopDetail.put("shop", shopInfo);
        shopDetail.put("productCategoryList", productCategoryDOList);
        return ResultDataVO.success(shopDetail);
    }

    /**
     * 根据商铺信息条件查询
     * @param productDTO 商品DTO对象
     * @param pageNum 当前页面
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/product")
    PageVO listProductByShop(@Validated ProductDTO productDTO,
                             @Range(min = 1,max = 10,message = "参数错误") Integer pageNum,
                             @Range(min = 1,max = 10,message = "参数错误") Integer pageSize){
        // 设置查询的status
        if(productDTO.getEnableStatus() == null){
            // 默认启用
            productDTO.setEnableStatus(1);
        }
        return productService.queryProductByCondition(productDTO,pageNum,pageSize);
    }
}
