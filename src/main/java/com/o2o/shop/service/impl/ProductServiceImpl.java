package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.mall.ProductDTO;
import com.o2o.shop.dto.shopmanager.ManageProductDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.ProductCategoryMapper;
import com.o2o.shop.mapper.ProductImgMapper;
import com.o2o.shop.mapper.ProductMapper;
import com.o2o.shop.model.ProductCategoryDO;
import com.o2o.shop.model.ProductDO;
import com.o2o.shop.model.ProductImgDO;
import com.o2o.shop.service.ProductService;
import com.o2o.shop.util.ImageStorage;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ProductVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopVO;
import lombok.extern.slf4j.Slf4j;
import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-15-20:28
 */
@Service
@Slf4j
public class ProductServiceImpl implements ProductService {

    @Autowired
    private ProductMapper productMapper;

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private HttpSession sessionOperator;

    @Autowired
    private ProductImgMapper productImgMapper;

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Override
    public PageVO queryProductByCondition(ProductDTO productDTO, Integer pageNum, Integer pageSize) {
        // 若ProductDTO为null,尝试从session中获取shopId
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        if (productDTO == null) {
            productDTO = new ProductDTO();
            if (currentShop == null) {
                log.error("获取商家商品列表异常,未登录");
                throw new BusinessException(ExceptionCodeEnum.EC20005);
            }
        }

        // 可输入的条件有: 商品名(模糊), 商品状态, 店铺Id, 商品类别
        // 拼接分页参数
        if (pageNum == null) {
            pageNum = 1;
        }
        if (pageSize == null) {
            // 默认数据量为5条
            pageSize = 5;
        }
        Page page = new Page(pageNum, pageSize);
        // 使用QueryWrapper实现条件查询
        QueryWrapper queryWrapper = new QueryWrapper();
        // 商品名模糊查询
        if (Strings.isNotBlank(productDTO.getProductName())) {
            queryWrapper.like("product_name", productDTO.getProductName());
        }
        // 商品状态,默认为启用
        if (productDTO.getEnableStatus() == null) {
            productDTO.setEnableStatus(1);
        }
        queryWrapper.eq("enable_status",productDTO.getEnableStatus());
        // 所属店铺id
        if (productDTO.getShopId() == null) {
            // 从Session中获取ShopId
            productDTO.setShopId(currentShop.getShopId());

        }
        queryWrapper.eq("shop_id", productDTO.getShopId());
        // 商品类别
        if (productDTO.getProductCategoryId() != null) {
            queryWrapper.eq("product_category_id", productDTO.getProductCategoryId());
        }
        Page productPage = productMapper.selectPage(page, queryWrapper);
        if(productPage.getRecords().isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return new PageVO(productPage.getRecords(), productPage.getRecords().size());
    }

    @Override
    public ProductVO queryProductById(Integer productId) {
        // 调用自定义的查询方法
        ProductVO productVO = productMapper.queryProductById(productId);
        if (productVO == null) {
            throw new BusinessException(ExceptionCodeEnum.EC30000);
        }
        return productVO;
    }

    @Override
    @Transactional
    public ResultDataVO createProduct(ManageProductDTO manageProductDTO, MultipartFile thumbnail, MultipartFile[] detailImg) {
        // 判断缩略图是否存在
        if (thumbnail.isEmpty()) {
            log.error("缩略图为空");
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        if (detailImg.length == 0) {
            log.error("详情图为空");
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        // 从Session中取出当前店铺信息
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);

        if (currentShop == null) {
            log.error("商品初始化失败,无法获取到当前用户的商铺信息");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        // 获取shopId
        int shopId = currentShop.getShopId();
        // 根据店铺信息获取目前商铺下的商品类别信息
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("shop_id",shopId);
        // 获取当前的商铺列表
        List<ProductCategoryDO> productCategoryList = productCategoryMapper.selectList(queryWrapper);
        // 使用anyMatch判断是否存在传入的类别,Mybatis即使查询不到数据也返回一个空对象,而非null,因此可不判断null
        if(!productCategoryList.isEmpty()){
            // 使用Set存储productCategoryId的方式降低查找的时间复杂度 O(N)->O(1)
            Set<Integer> productCategorySet = productCategoryList.stream()
                    .map(ProductCategoryDO::getProductCategoryId)
                    .collect(Collectors.toSet());
            if(!productCategorySet.contains(manageProductDTO.getProductCategoryId())){
                log.error("请求类别信息与当前实际商品类别不匹配" );
                throw new BusinessException(ExceptionCodeEnum.EC30001);
            }
        }else{
            log.error("当前商铺下没有商品类别");
            throw new BusinessException(ExceptionCodeEnum.EC30008);
        }

        // 先处理商品类相关信息
        ProductDO productDO = new ProductDO();
        BeanUtils.copyProperties(manageProductDTO, productDO);
        // 设置商品所属店铺id
        productDO.setShopId(shopId);
        // 获取缩略图的相对路径
        String thumbnailPath = imageStorage.handImage(thumbnail, GlobalConstant.MALL_IMAGE, shopId);
        productDO.setImgAddr(thumbnailPath);
        // 先将商品信息写入数据库
        int effectNum = productMapper.insert(productDO);
        if (effectNum < 1) {
            log.error("创建商品信息失败");
            throw new BusinessException(ExceptionCodeEnum.EC30005);
        }
        // 处理商品详情图,最多6张图
        List<ProductImgDO> productImgList = handleProductDetailImages(detailImg, shopId, productDO.getProductId());
        if (productImgList.isEmpty()) {
            log.error("商品详情图为空");
            throw new BusinessException(ExceptionCodeEnum.EC30006);
        }
        productImgList.forEach(productImgDO -> {
            int insertNum = productImgMapper.insert(productImgDO);
            if (insertNum < 1) {
                log.error("商品信息详情图操作失败");
                handleUnusedImage(productImgList,false);
                throw new BusinessException(ExceptionCodeEnum.EC10009);
            }
        });
        // 设置商品为上架状态
        productDO.setEnableStatus(1);
        effectNum = productMapper.updateById(productDO);
        if (effectNum < 1) {
            log.error("更新商品状态失败,未能正常上架商品");
            throw new BusinessException(ExceptionCodeEnum.EC30007);
        }
        return ResultDataVO.success(null);
    }


    @Override
    @Transactional
    public ResultDataVO updateProduct(ManageProductDTO manageProductDTO, MultipartFile thumbnail, MultipartFile[] detailImg) {
        // 从Session中取出当前店铺信息
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        if (currentShop == null) {
            log.error("修改商品失败,无法获取到当前用户的商铺信息");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        // 更新前先查
        ProductDO productDO = productMapper.selectById(manageProductDTO.getProductId());
        if (productDO == null) {
            log.error("无法获取到当前商品信息");
            throw new BusinessException(ExceptionCodeEnum.EC30000);
        }
        // 获取原缩略图地址
        String beforeImage = productDO.getImgAddr();
        String thumbnailPath = null;
        // 删除原缩略图
        if (thumbnail != null && !thumbnail.isEmpty()) {
            // 删除图片
            boolean deleteImage = imageStorage.deleteImageByPath(beforeImage);
            if (!deleteImage) {
                log.error("商品管理, 删除{}原缩略图失败", currentShop.getShopId());
            }
            thumbnailPath = imageStorage.handImage(thumbnail, GlobalConstant.MALL_IMAGE, currentShop.getShopId());
        }
        // 获取新属性
        BeanUtils.copyProperties(manageProductDTO, productDO);
        // 设置当前的缩略图地址
        productDO.setImgAddr(thumbnailPath);
        // 设置shopId
        productDO.setShopId(manageProductDTO.getShopId());

        // 是否需要更新详情图
        if (detailImg != null && detailImg.length > 0) {
            // 先查询旧的的详情图
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("product_id", manageProductDTO.getProductId());
            List<ProductImgDO> sourceProductImg = productImgMapper.selectList(queryWrapper);
            if (sourceProductImg == null || sourceProductImg.isEmpty()) {
                log.error("详情图为空,无法继续执行操作");
                throw new BusinessException(ExceptionCodeEnum.EC10009);
            }
            // 删除旧详情图
            handleUnusedImage(sourceProductImg, true);

            // 新详情图存入
            List<ProductImgDO> productImgList = handleProductDetailImages(detailImg, manageProductDTO.getShopId(), manageProductDTO.getProductId());
            if (productImgList != null && !productImgList.isEmpty()) {
                productImgList.forEach(productImgDO -> {
                    int effectNum = productImgMapper.insert(productImgDO);
                    if (effectNum < 1) {
                        log.error("详情图更新失败,未存入数据库");
                        throw new BusinessException(ExceptionCodeEnum.EC10009);
                    }
                });
            }

        }
        // 更新商品表信息
        int effectNum = productMapper.updateById(productDO);
        if (effectNum < 1) {
            log.error("商品信息更新失败");
            throw new BusinessException(ExceptionCodeEnum.EC30009);
        }
        return ResultDataVO.success(null);
    }

    /**
     * 处理详情图片
     *
     * @param multipartFiles 图片数组
     * @return
     */
    private List<ProductImgDO> handleProductDetailImages(MultipartFile[] multipartFiles, Integer shopId, Integer productId) {
        int maxImageSize = 6;
        List<ProductImgDO> productImgDOList = new ArrayList<>(6);
        if (multipartFiles.length < maxImageSize) {
            maxImageSize = multipartFiles.length;
        }
        for (int i = 0; i < maxImageSize; i++) {
            if (!multipartFiles[i].isEmpty()) {
                String imgPath = imageStorage.handImage(multipartFiles[i], "mall", shopId);
                ProductImgDO productImgDO = ProductImgDO.builder().imgAddr(imgPath).productId(productId).build();
                productImgDOList.add(productImgDO);
            }
        }
        return productImgDOList;
    }

    /**
     * 操作失败时,删除本地图片/数据库(可选)图片
     *
     * @param productImgDOList
     * @param deleteDatabase
     */
    private void handleUnusedImage(List<ProductImgDO> productImgDOList, boolean deleteDatabase) {
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        productImgDOList.forEach(productImgDO -> {
            boolean deleteImage = imageStorage.deleteImageByPath(productImgDO.getImgAddr());
            if (!deleteImage) {
                log.error("删除{}原缩略图失败", currentShop.getShopId());
            }
            if (deleteDatabase) {
                int effectNum = productImgMapper.deleteById(productImgDO);
                if (effectNum < 1) {
                    log.error("数据库删除productId为{}详情图信息失败", productImgDO.getProductId());
                    throw new BusinessException(ExceptionCodeEnum.EC10009);
                }
            }
        });
    }
}
