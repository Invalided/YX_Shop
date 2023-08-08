package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.mall.ProductDTO;
import com.o2o.shop.dto.shopmanager.ManageProductDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.service.ProductCategoryService;
import com.o2o.shop.service.ProductService;
import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * ProductManageController 商品管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-20-18:20
 *
 */
@RestController
@RequestMapping("/manager")
@Validated
@Slf4j
public class ProductManageController {

    @Autowired
    private ProductService productService;


    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 根据商铺id获取当前已经上架的商品列表
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/product/list")
    PageVO queryProductListByShopId(ProductDTO productDTO,
                                    @Range(min = 1,max = 10,message = "pageNum不合法") Integer pageNum,
                                    @Range(min = 1,max = 10,message = "pageSize不合法") Integer pageSize){
        // 此处查询不需要DTO对象
        return productService.queryProductByCondition(productDTO,pageNum,pageSize);
    }

    /**
     * 获取指定商铺的商品类别信息
     * @param shopId 商铺id
     * @return
     */
    @GetMapping("/category/list")
    PageVO queryProductCategoryList(@Range(min = 1,max = 5000,message = "商铺id不合法") Integer shopId){
        PageVO pageVO = productCategoryService.queryProductCategoryById(shopId);
        if(pageVO.getTotal() == 0){
            log.error("创建商品时需至少需要一个商品类别");
            throw new BusinessException(ExceptionCodeEnum.EC30008);
        }
        return pageVO;
    }


    /**
     * 创建商品
     * @param manageProductDTO 商品管理DTO对象
     * @param thumbnail 缩略图(主图,不能为空)
     * @param detailImg 详情图(至少一张)
     * @return
     */
    @PostMapping("/product/operation")
    ResultDataVO createProduct(@Validated(AddGroup.class) ManageProductDTO manageProductDTO,
                               @RequestParam MultipartFile thumbnail,
                               @RequestParam MultipartFile[] detailImg){
        return productService.createProduct(manageProductDTO,thumbnail,detailImg);
    }

    /**
     * 修改商品信息
     * @param manageProductDTO 商品管理DTO对象
     * @param thumbnail 缩略图
     * @param detailImg 详情图
     * @return
     */
    @PutMapping("/product/edit")
    ResultDataVO modifyProduct(@Validated(UpdateGroup.class) ManageProductDTO manageProductDTO,
                               @RequestParam(required = false) MultipartFile thumbnail,
                               @RequestParam(required = false) MultipartFile[] detailImg){
        return productService.updateProduct(manageProductDTO,thumbnail,detailImg);
    }
}
