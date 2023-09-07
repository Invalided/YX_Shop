package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.superadmin.ShopCategoryDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.ShopCategoryMapper;
import com.o2o.shop.model.ShopCategoryDO;
import com.o2o.shop.service.ShopCategoryService;
import com.o2o.shop.util.CacheConvert;
import com.o2o.shop.util.ImageStorage;
import com.o2o.shop.util.RedisOperator;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopCategoryVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

/**
 * @author 勿忘初心
 * @since 2023-07-09-0:03
 */
@Service
@Slf4j
public class ShopCategoryServiceImpl implements ShopCategoryService {

    @Autowired
    private ShopCategoryMapper shopCategoryMapper;

    @Autowired
    private RedisOperator redisOperator;

    @Autowired
    private CacheConvert cacheOperator;

    @Autowired
    private ImageStorage imageStorage;

    @Override
    public PageVO queryAllShopCategoryListPage(Integer pageNum, Integer pageSize, ShopCategoryBO shopCategoryCondition, boolean allPage) {

        /**
         * 1. ShopCategoryCondition == null 所有一级分类(未实例化)
         * 2. ShopCategoryCondition != null 所有二级分类(实例化但无属性) 同 isEmpty
         * 3. ShopCategoryCondition != null && .parent != null &&. parent.shopCategoryId != null
         * (指定一级分类下的所有二级分类)
         * 4. allPage 指定返回所有的分类信息
         */
        // 设置缓存key
        StringBuffer shopCategoryCacheKey = null;
        // 创建返回数据对象
        List<ShopCategoryVO> shopCategoryVOList;
        // 一二级类别进行缓存查询
        if (!allPage) {
            // 创建当前业务缓存key
            shopCategoryCacheKey = new StringBuffer(ShopCategoryService.SHOP_CATEGORY);
            if (shopCategoryCondition == null) {
                // 所有一级分类
                shopCategoryCacheKey.append("_primary");
            } else if (shopCategoryCondition.isEmpty()) {
                // 所有二级分类
                shopCategoryCacheKey.append("_secondary");
            }

            if (redisOperator.ping()) {
                // 是否存在缓存
                if (redisOperator.keyIsExist(shopCategoryCacheKey.toString())) {
                    // 获取数据返回
                    shopCategoryVOList = cacheOperator.readCache(shopCategoryCacheKey.toString(),
                            new ArrayList<ShopCategoryVO>());
                    return new PageVO(shopCategoryVOList, shopCategoryVOList.size());
                }
            }
        }

        // 分页条件
        Page<ShopCategoryDO> page = new Page<>(pageNum, pageSize);
        // 查询数据
        Page<ShopCategoryVO> shopCategoryVOPage = shopCategoryMapper.queryShopCategoryListPage(page,
                shopCategoryCondition, allPage);
        // 条件转换
        shopCategoryVOList = shopCategoryVOPage.getRecords();
        // 存入缓存,排除全类别查询
        if (redisOperator.ping() && !allPage) {
            cacheOperator.writeCache(shopCategoryCacheKey.toString(), shopCategoryVOList);
        }

        return new PageVO(shopCategoryVOList, shopCategoryVOList.size());
    }

    @Override
    public ResultDataVO createShopCategory(ShopCategoryDTO shopCategoryDTO, MultipartFile multipartFile) {
        // 取出图片
        if (multipartFile.isEmpty()) {
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        // 获取文件存储的相对路径
        String relativePath = imageStorage.handImage(multipartFile, GlobalConstant.SHOP_CATEGORY_IMAGE, null);
        // 设置DTO对象的属性
        shopCategoryDTO.setShopCategoryImg(relativePath);
        // 判断类别,若为二级分类,则需要查看一级分类是否存在
        if (shopCategoryDTO.getParentId() != null) {
            // 查询该一级分类是否存在
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("shop_category_id", shopCategoryDTO.getParentId());
            queryWrapper.isNull("parent_id");
            ShopCategoryDO primaryShopCategory = shopCategoryMapper.selectOne(queryWrapper);
            if (primaryShopCategory == null) {
                log.error("无法新增类别,一级类别不存在");
                throw new BusinessException(ExceptionCodeEnum.EC40005);
            }
        }
        ShopCategoryDO shopCategoryDO = new ShopCategoryDO();
        // 拷贝属性，写入到数据库
        BeanUtils.copyProperties(shopCategoryDTO, shopCategoryDO);
        int effectNum = shopCategoryMapper.insert(shopCategoryDO);
        if (effectNum < 1) {
            log.error("商铺类别创建失败");
            throw new BusinessException(ExceptionCodeEnum.EC40005);
        }
        if (shopCategoryDTO.getParentId() == null) {
            // 清除一级分类缓存
            cacheOperator.clearCache(SHOP_CATEGORY + "_primary");
        } else {
            // 清除二级缓存
            cacheOperator.clearCache(SHOP_CATEGORY + "_secondary");
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO updateShopCategory(ShopCategoryDTO shopCategoryDTO, MultipartFile multipartFile) {
        // 缓存key
        StringBuffer cacheKey = new StringBuffer(SHOP_CATEGORY);
        // 更新前先查
        ShopCategoryDO sourceShopCategoryDO = shopCategoryMapper.selectById(shopCategoryDTO.getShopCategoryId());
        if (sourceShopCategoryDO == null) {
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        // 拼接缓存key
        if (sourceShopCategoryDO.getParentId() == null) {
            cacheKey.append("_primary");
        } else {
            cacheKey.append("_secondary");
        }
        // 是否更新图片
        if (multipartFile != null && multipartFile.isEmpty()) {
            // 取出图片,并赋值
            shopCategoryDTO.setShopCategoryImg(imageStorage.handImage(multipartFile, "ShopCategory", null));
        }
        BeanUtils.copyProperties(shopCategoryDTO, sourceShopCategoryDO);
        int effectNum = shopCategoryMapper.updateById(sourceShopCategoryDO);
        if (effectNum < 0) {
            log.error("商铺类别更新失败(superAdmin)");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        // 清理缓存
        if (redisOperator.ping()) {
            cacheOperator.clearCache(cacheKey.toString());
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO deleteShopCategoryById(Integer id) {
        // 删除前先查
        ShopCategoryDO sourceShopCategoryDO = new ShopCategoryDO();
        sourceShopCategoryDO = shopCategoryMapper.selectById(id);
        if (sourceShopCategoryDO == null) {
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        StringBuffer cacheKey = new StringBuffer(SHOP_CATEGORY);
        // 是否为一级列表
        if (sourceShopCategoryDO.getParentId() == null) {
            // 获取一级类别对应的的列表
            QueryWrapper queryWrapper = new QueryWrapper();
            queryWrapper.eq("parent_id", sourceShopCategoryDO.getShopCategoryId());
            // 获取二级类别
            List<ShopCategoryDO> secondaryVOList = shopCategoryMapper.selectList(queryWrapper);
            if (!secondaryVOList.isEmpty()) {
                log.error("待删除的一级列表包含二级列表,无法继续操作");
                throw new BusinessException(ExceptionCodeEnum.EC10011);
            } else {
                cacheKey.append("_primary");
            }
        } else {
            cacheKey.append("_secondary");
        }
        // 无子项的一级分类或普通二级分类直接删除
        int effectNum = shopCategoryMapper.deleteById(sourceShopCategoryDO.getShopCategoryId());
        if (effectNum < 1) {
            log.error("删除类别信息失败(superAdmin");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        // 清除对应缓存
        if (redisOperator.ping()) {
            cacheOperator.clearCache(cacheKey.toString());
        }
        return ResultDataVO.success(null);
    }

}
