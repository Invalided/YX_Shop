package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.mall.AwardDTO;
import com.o2o.shop.dto.shopmanager.ManageAwardDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.AwardMapper;
import com.o2o.shop.model.AwardDO;
import com.o2o.shop.service.AwardService;
import com.o2o.shop.util.ImageStorage;
import com.o2o.shop.vo.AwardVO;
import com.o2o.shop.vo.PageVO;
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

/**
 * @author 勿忘初心
 * @since 2023-07-23-13:41
 */
@Service
@Slf4j
public class AwardServiceImpl implements AwardService {

    @Autowired
    private AwardMapper awardMapper;

    @Autowired
    private HttpSession sessionOperator;

    @Autowired
    private ImageStorage imageStorage;


    @Override
    public PageVO queryAwardByCondition(AwardDTO awardDTO, Integer pageNum, Integer pageSize) {

        // 若ProductDTO为null,尝试从session中获取shopId
        if (awardDTO.getShopId() == null) {
            ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
            if (currentShop == null) {
                log.error("获取商家商品列表异常,未登录");
                throw new BusinessException(ExceptionCodeEnum.EC20005);
            }
            awardDTO.setShopId(currentShop.getShopId());
        }
        // 默认不分页
        if(pageNum  == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = -1;
        }
        Page page = new Page(pageNum,pageSize);
        QueryWrapper queryWrapper = new QueryWrapper();
        // 查询条件赋值
        if(Strings.isNotBlank(awardDTO.getAwardName())){
            // 模糊查询
            queryWrapper.like("award_name",awardDTO.getAwardName());
        }
        if(awardDTO.getEnableStatus() == null){
            awardDTO.setEnableStatus(1);
        }
        queryWrapper.eq("enable_status",awardDTO.getEnableStatus());
        // 直接赋值即可
        if(awardDTO.getShopId() != null){
            queryWrapper.eq("shop_id",awardDTO.getShopId());
        }
        Page awardPage = awardMapper.selectPage(page,queryWrapper);
        return new PageVO(awardPage.getRecords(),awardPage.getRecords().size());
    }

    @Override
    public AwardVO queryAwardById(Integer awardId) {
        AwardDO awardDO = awardMapper.selectById(awardId);
        if(awardDO == null){
            log.error("奖品信息不存在");
            throw new BusinessException(ExceptionCodeEnum.EC70000);
        }
        AwardVO awardVO = new AwardVO();
        BeanUtils.copyProperties(awardDO, awardVO);
        return awardVO;
    }

    @Override
    @Transactional
    public ResultDataVO createAward(ManageAwardDTO manageAwardDTO, MultipartFile thumbnail) {
        if(thumbnail.isEmpty()){
            log.error("未上传奖品图片");
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        // 从Session中取出当前店铺信息
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        if (currentShop == null) {
            log.error("奖品初始化失败,无法获取到当前用户的商铺信息");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        // 获取shopId
        int shopId = currentShop.getShopId();

        AwardDO awardDO = new AwardDO();
        BeanUtils.copyProperties(manageAwardDTO, awardDO);
        // 设置shopId
        awardDO.setShopId(shopId);
        // 处理奖品图片
        String thumbnailPath = imageStorage.handImage(thumbnail,"mall",shopId);
        awardDO.setAwardImg(thumbnailPath);
        // 启用奖品
        awardDO.setEnableStatus(1);
        int effectNum = awardMapper.insert(awardDO);
        if(effectNum < 1){
            log.error("奖品信息创建失败");
            throw new BusinessException(ExceptionCodeEnum.EC70001);
        }
        return ResultDataVO.success(null);
    }

    @Override
    @Transactional
    public ResultDataVO updateAward(ManageAwardDTO manageAwardDTO, MultipartFile thumbnail) {
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        if (currentShop == null) {
            log.error("奖品初始化失败,无法获取到当前用户的商铺信息");
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        int shopId = currentShop.getShopId();
        manageAwardDTO.setShopId(shopId);
        // 更新前先查
        AwardDO awardDO = awardMapper.selectById(manageAwardDTO.getAwardId());
        if(awardDO == null){
            log.error("无法修改,奖品信息不存在");
            throw new BusinessException(ExceptionCodeEnum.EC70000);
        }
        String beforeImage = awardDO.getAwardImg();
        String thumbnailPath = null;
        // 是否更新缩略图
        if(thumbnail != null && !thumbnail.isEmpty()){
            // 删除图片
            boolean deleteImage = imageStorage.deleteImageByPath(beforeImage);
            if (!deleteImage) {
                log.error("奖品管理, 删除{}原缩略图失败", currentShop.getShopId());
            }
            thumbnailPath = imageStorage.handImage(thumbnail, GlobalConstant.MALL_IMAGE, currentShop.getShopId());
        }
        // 新属性获取
        BeanUtils.copyProperties(manageAwardDTO,awardDO);
        awardDO.setAwardImg(thumbnailPath);
        int effectNum = awardMapper.updateById(awardDO);
        if(effectNum < 1){
            log.error("更新奖品信息失败,未正常写入数据库");
            throw new BusinessException(ExceptionCodeEnum.EC20002);
        }
        return ResultDataVO.success(null);
    }
}
