package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.mall.ProductCategoryDTO;
import com.o2o.shop.service.ProductCategoryService;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.util.List;

/**
 * ProductCategoryManageController 商家商品类别管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-21-20:44
 */
@RestController
@RequestMapping("/manager")
@Validated
public class ProductCategoryManageController {

    @Autowired
    private ProductCategoryService productCategoryService;

    /**
     * 批量新增商品类别
     * @param productCategoryList 商品类别列表,需要使用数组传参,至少一条
     * @return
     */
    @PostMapping("/category/create")
    ResultDataVO createProductCategory(@RequestBody @NotEmpty(message = "至少包含一个类上信息")
                                       @Valid List<ProductCategoryDTO>  productCategoryList){
       return productCategoryService.batchInsertProductCategory(productCategoryList);
    }

    /**
     * 商品类别删除
     * @param productCategoryDTO
     * @return
     */
    @DeleteMapping("/category/delete")
    ResultDataVO deleteProductCategory(@Validated(UpdateGroup.class)
                                       @RequestBody ProductCategoryDTO productCategoryDTO){
        return productCategoryService.deleteProductCategoryById(productCategoryDTO);
    }


}
