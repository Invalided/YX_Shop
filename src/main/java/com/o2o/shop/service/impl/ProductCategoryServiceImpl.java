package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.mall.ProductCategoryDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.ProductCategoryMapper;
import com.o2o.shop.mapper.ProductMapper;
import com.o2o.shop.model.ProductCategoryDO;
import com.o2o.shop.service.ProductCategoryService;
import com.o2o.shop.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-15-18:07
 */
@Service
@Slf4j
public class ProductCategoryServiceImpl implements ProductCategoryService {

    @Autowired
    private ProductCategoryMapper productCategoryMapper;

    @Autowired
    private HttpSession sessionOperator;

    @Autowired
    private ProductMapper productMapper;

    @Override
    public PageVO queryProductCategoryById(Integer shopId) {
        ShopVO shopVO = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        // shopId属性判断
        if(shopId == null){
            if(shopVO == null){
                throw new BusinessException(ExceptionCodeEnum.EC20005);
            }
            shopId = shopVO.getShopId();
        }

        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("shop_id", shopId);
        queryWrapper.orderByDesc("priority");
        List<ProductCategoryDO> productCategoryDOList = productCategoryMapper.selectList(queryWrapper);
        // 复制属性到VO对象
        List<ProductCategoryVO> productCategoryVOList = productCategoryDOList.stream().
                map(productCategoryDO ->{
                    ProductCategoryVO productCategoryVO = new ProductCategoryVO();
                    BeanUtils.copyProperties(productCategoryDO,productCategoryVO);
                    return productCategoryVO;
                }).collect(Collectors.toList());
        return new PageVO(productCategoryVOList,productCategoryDOList.size());
    }

    @Override
    public ResultDataVO batchInsertProductCategory(List<ProductCategoryDTO> productCategoryDTOList) {
        // todo 重名处理
        // 获取当前商铺的id
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        // 获取当前商家信息
        LocalAuthVO localAuthVO = (LocalAuthVO) sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
        if(currentShop == null){
            log.error("新增商铺类别出错,无权限");
            throw new BusinessException(ExceptionCodeEnum.EC10004);
        }
        // 判断所属信息是否一致
        if(!currentShop.getOwnerId().equals(localAuthVO.getPersonInfo().getUserId())){
            log.error("无法创建商品类别,商家信息与Session信息不一致");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }

        // 将DTO对象转为DO对象
        List<ProductCategoryDO> productCategoryDOList = productCategoryDTOList.stream().
                map(ProductCategoryDTO -> {
                    ProductCategoryDO productCategoryDO = new ProductCategoryDO();
                    BeanUtils.copyProperties(ProductCategoryDTO, productCategoryDO);
                    // 设置所属的商铺id
                    productCategoryDO.setShopId(currentShop.getShopId());
                    return productCategoryDO;
                }).collect(Collectors.toList());

        // 遍历存入数据库中
        productCategoryDOList.forEach(
                productCategoryDO -> {
                    int effectNum = productCategoryMapper.insert(productCategoryDO);
                    if(effectNum < 1){
                        log.error("批量新增商品类别失败");
                        throw new BusinessException(ExceptionCodeEnum.EC10010);
                    }
                }
        );
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteProductCategoryById(ProductCategoryDTO productCategoryDTO) {
        // 删除前先查
        ProductCategoryDO productCategoryDO = productCategoryMapper.selectById(productCategoryDTO.getProductCategoryId());
        if(productCategoryDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC30001);
        }
        // 当前商品类别下是否有有商品
        QueryWrapper productQueryByCategory = new QueryWrapper();
        productQueryByCategory.eq("product_category_id",productCategoryDO.getProductCategoryId());
        productQueryByCategory.eq("enable_status",1);
        List<ProductCategoryDO> productCategoryList = productMapper.selectList(productQueryByCategory);
        if(!productCategoryList.isEmpty()){
            log.error("删除商品类别失败,当前类别有在售卖的商品");
            throw new BusinessException(ExceptionCodeEnum.EC30010);
        }
        if(productCategoryDTO.getShopId() == null){
            // 尝试从session中获取shop信息
            ShopVO shopVO = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
            if(shopVO != null ){
                productCategoryDTO.setShopId(shopVO.getShopId());
            }else{
                log.error("无法删除商品类别信息，未能获取当前商铺信息");
                throw new BusinessException(ExceptionCodeEnum.EC10004);
            }
        }
        // 判断店铺信息是否匹配
        if(!productCategoryDTO.getShopId().equals(productCategoryDO.getShopId())){
            log.error("{} 请求删除的店铺与信息不符",productCategoryDO.getProductCategoryName());
            throw new BusinessException(ExceptionCodeEnum.EC10004);
        }
        int effectNum = productCategoryMapper.deleteById(productCategoryDO);
        if(effectNum < 1){
            log.error("删除商品类别信息失败");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        return ResultDataVO.success(null);
    }
}
