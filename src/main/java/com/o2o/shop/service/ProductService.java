package com.o2o.shop.service;

import com.o2o.shop.dto.mall.ProductDTO;
import com.o2o.shop.dto.shopmanager.ManageProductDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ProductVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.web.multipart.MultipartFile;

/**
 * 商品Service层
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
public interface ProductService{
    /**
     * 根据条件查询商品信息,该方法使用MP的QueryWrapper实现条件查询
     * @param productDTO 商品DTO
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    PageVO queryProductByCondition(ProductDTO productDTO, Integer pageNum, Integer pageSize);

    /**
     * 根据商品id获取商品的详细信息，商品详情图等
     * @param productId
     * @return
     */
    ProductVO queryProductById(Integer productId);

    /**
     * 创建商品
     * @param manageProductDTO 商品管理DTO对象
     * @param thumbnail 缩略图
     * @param detailImg 详情图
     * @return
     */

    ResultDataVO createProduct(ManageProductDTO manageProductDTO, MultipartFile thumbnail,
                               MultipartFile[] detailImg);

    /**
     * 修改商品信息
     * @param manageProductDTO 商品管理DTO对象
     * @param thumbnail 缩略图
     * @param detailImg 详情图
     * @return
     */
    ResultDataVO updateProduct(ManageProductDTO manageProductDTO, MultipartFile thumbnail,
                               MultipartFile[] detailImg);
}
