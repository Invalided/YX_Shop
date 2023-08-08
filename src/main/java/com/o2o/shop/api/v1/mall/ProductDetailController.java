package com.o2o.shop.api.v1.mall;

import com.o2o.shop.service.ProductService;
import com.o2o.shop.vo.ProductVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * ProductDetailController 商品详情页面
 * @author 勿忘初心
 * @since 2023-07-15-21:27
 *
 */
@RestController
@Validated
@RequestMapping("/mall")
public class ProductDetailController {

    @Autowired
    private ProductService productService;

    /**
     * 根据Id获取商品的详细信息
     * @param productId
     * @return
     */
    @GetMapping("/product/{id}")
    ProductVO queryProductById(@NotNull(message = "商品id不能为空")
                               @Range(min = 1,max = 3000,message = "参数错误")
                               @PathVariable(name = "id")Integer productId){
        return productService.queryProductById(productId);
    }
}
