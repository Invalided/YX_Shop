package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.ShopBO;
import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.shopmanager.ManageShopDTO;
import com.o2o.shop.dto.superadmin.ShopDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.ShopMapper;
import com.o2o.shop.model.AreaDO;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ShopDO;
import com.o2o.shop.service.LocalAuthService;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.util.ImageStorage;
import com.o2o.shop.vo.LocalAuthVO;
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
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-08-15:53
 */
@Service
@Slf4j
public class ShopServiceImpl implements ShopService {
    @Autowired
    private ShopMapper shopMapper;

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private ImageStorage imageStorage;

    @Autowired
    private HttpSession sessionOperator;

    @Override
    public PageVO queryShopListPage(Integer pageNum, Integer pageSize, ShopDTO shopDTO) {
        // ShopCondition 条件拼装
        ShopBO shopCondition = new ShopBO();
        if(shopDTO != null){
            // 获取拼接后的条件
            shopCondition = handleShopQueryCondition(shopDTO);
        }
        // 拼接分页参数
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            // 默认不分页，若需分页可调整
            pageSize = -1;
        }
        Page<ShopDO> page = new Page<>(pageNum,pageSize);
        // 自定义方法，直接返回VO对象
        Page<ShopVO> shopPage = shopMapper.queryShopListPage(page,shopCondition);
        // 将Page转为list
        List<ShopVO> shopVOList = shopPage.getRecords();
        if(shopVOList.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        // 将当前用户的所拥有的店铺列表信息存入Session
        sessionOperator.setAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP,shopVOList);
        return new PageVO(shopVOList,shopVOList.size());
    }

    @Override
    public ShopVO queryShopById(Integer id) {
        // 使用自定义查询语句
        ShopVO shopVO = shopMapper.queryByShopId(id);
        if(shopVO == null){
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        return shopVO;
    }

    @Override
    public ResultDataVO updateShop(ShopDTO shopDTO) {
        // 更新前先查
        ShopDO dbShopDO = shopMapper.selectById(shopDTO.getShopId());
        if(dbShopDO == null) {
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        // 复制shopDTO中的值
        BeanUtils.copyProperties(shopDTO, dbShopDO);
        int effectNum = shopMapper.updateById(dbShopDO);
        if(effectNum < 0){
            log.error("更新商铺信息失败");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        return ResultDataVO.success(null);
    }



    @Override
    @Transactional
    public ResultDataVO createShop(ManageShopDTO managerShopDTO, MultipartFile multipartFile) {
        // 验证用户信息
        LocalAuthVO sessionAuthUser = localAuthService.getUserAuthSession();
        if(sessionAuthUser == null || sessionAuthUser.getPersonInfo() == null){
            // 用户未登录
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        if(!sessionAuthUser.getPersonInfo().getUserType().equals(GlobalConstant.RoleType.MANAGER.getValue())){
            // 无权限
            throw new BusinessException(ExceptionCodeEnum.EC10004);
        }

        // 获取创建店铺的用户id
        int ownerId = sessionAuthUser.getPersonInfo().getUserId();
        // 验证店铺数量是否已达上限,todo 可优化
        List<ShopVO> shopVOList = (List<ShopVO>)sessionOperator.getAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP);
        int maxShopNum = 5;
        if(shopVOList != null){
            if(shopVOList.size() == maxShopNum){
                throw new BusinessException(ExceptionCodeEnum.EC40003);
            }
        }else{
            shopVOList = new ArrayList<>(5);
        }
        // 检查图片是否存在
        if(multipartFile.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        // 复制属性
        ShopDO shopDO = new ShopDO();
        BeanUtils.copyProperties(managerShopDTO,shopDO);
        // 设置创建者id
        shopDO.setOwnerId(ownerId);
        int effectNum = shopMapper.insert(shopDO);
        if(effectNum < 1){
            log.error("商铺初始化失败,基本信息存储失败");
            throw new BusinessException(ExceptionCodeEnum.EC40001);
        }

        // 获取图片相对位置,需要shopId,商铺图片存储使用名为mall
        String shopImgRelative = imageStorage.handImage(multipartFile,GlobalConstant.MALL_IMAGE,shopDO.getShopId());
        if(shopImgRelative == null){
            log.error("商铺初始化失败,图片相对路径获取失败");
            throw new BusinessException(ExceptionCodeEnum.EC40001);
        }
        shopDO.setShopImg(shopImgRelative);
        effectNum = shopMapper.updateById(shopDO);
        if(effectNum < 1){
            log.error("商铺创建失败,写入商铺图片异常");
            throw new BusinessException(ExceptionCodeEnum.EC40002);
        }
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO updateShop(ManageShopDTO managerShopDTO, MultipartFile multipartFile) {
        // 更新前先查
        ShopDO dbShopDO = shopMapper.selectById(managerShopDTO.getShopId());
        // 判断店铺是否存在且已通过审核
        if(dbShopDO == null || !dbShopDO.getEnableStatus().equals(1)) {
            throw new BusinessException(ExceptionCodeEnum.EC10002);
        }
        // 名称
        if(Strings.isNotBlank(managerShopDTO.getShopName())){
            dbShopDO.setShopName(managerShopDTO.getShopName());
        }
        // 区域
        if(managerShopDTO.getShopId() != null){
            dbShopDO.setShopId(managerShopDTO.getShopId());
        }
        // 地址
        if(Strings.isNotBlank(managerShopDTO.getShopAddr())){
            dbShopDO.setShopAddr(managerShopDTO.getShopAddr());
        }
        // 联系电话
        if(Strings.isNotBlank(managerShopDTO.getPhone())){
            dbShopDO.setPhone(managerShopDTO.getPhone());
        }
        String shopImgRelative = null;
        if(multipartFile != null && !multipartFile.isEmpty()){
            shopImgRelative = imageStorage.handImage(multipartFile,GlobalConstant.MALL_IMAGE,managerShopDTO.getShopId());
        }
        // 更新图片
        if(shopImgRelative != null){
            dbShopDO.setShopImg(shopImgRelative);
        }
        // 店铺描述
        if(Strings.isNotBlank(managerShopDTO.getShopDesc())){
            dbShopDO.setShopDesc(managerShopDTO.getShopDesc());
        }
        int effectNum = shopMapper.updateById(dbShopDO);
        if(effectNum < 1){
            throw new BusinessException(ExceptionCodeEnum.EC40004);
        }
        return ResultDataVO.success(null);
    }


    @Override
    public ResultDataVO deleteShop(ManageShopDTO managerShopDTO) {
        // 删除前先查
        ShopDO shopDO = shopMapper.selectById(managerShopDTO.getShopId());
        if(shopDO == null){
            log.error("无法删除店铺, 未能获取店铺信息");
            throw new BusinessException(ExceptionCodeEnum.EC40000);
        }
        // 简单实现店铺删除
        int effectNum = shopMapper.deleteById(shopDO);
        if(effectNum < 1){
            log.error("商铺删除失败");
            throw new BusinessException(ExceptionCodeEnum.EC10010);
        }
        // 处理session
        List<ShopVO> shopVOList = (List<ShopVO>) sessionOperator.getAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP);
        if(shopVOList != null){
            Iterator<ShopVO> iterator = shopVOList.iterator();
            while(iterator.hasNext()){
                ShopVO shop = iterator.next();
                if(shop.getShopId().equals(managerShopDTO.getShopId())){
                    iterator.remove();
                }
            }
        }
        // 使用流的方式遍历
        shopVOList.forEach(shopVO -> log.info(shopVO.toString()));
        // 重新设置session
        sessionOperator.setAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP,shopVOList);
        return ResultDataVO.success(null);
    }

    @Override
    public ResultDataVO shopManagementInfo(Integer shopId) {

        // 获取用户可管理的列表
        List<ShopVO> managerShopList = (List<ShopVO>) sessionOperator.getAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP);
        if(managerShopList != null && !managerShopList.isEmpty()){
            // 使用流的方式将对象重新映射,Function.identity()指定value为原始对象
            Map<Integer,ShopVO> shopVOMap = managerShopList.stream()
                    .collect(Collectors.toMap(ShopVO::getShopId,
                            Function.identity()));

            if(shopVOMap.containsKey(shopId)){
                // 店铺处于审核状态或被禁用
                if(!shopVOMap.get(shopId).getEnableStatus().equals(1)){
                    log.error("非法访问,当前店铺状态 {}", shopVOMap.get(shopId).getEnableStatus());
                    throw new BusinessException(ExceptionCodeEnum.EC40006);
                }
                sessionOperator.setAttribute(GlobalConstant.CURRENT_SHOP,shopVOMap.get(shopId));
                return ResultDataVO.success(null);
            }
        }
        log.error("获取商铺权限失败,无对应商铺权限或未登录");
        throw new BusinessException(ExceptionCodeEnum.EC10004);
    }


    /**
     * 根据传入的DTO对象拼接出需要查询的条件参数
     * @param shopDTO 商铺传输对象
     * @return
     */
    public ShopBO handleShopQueryCondition(ShopDTO shopDTO){
        ShopBO shopCondition = new ShopBO();
        // 类别信息,含有自定义对象，使用BO类
        ShopCategoryBO shopCategoryBO = null;
        if(shopDTO.getParentId() != null){
            // 一级类别
            shopCategoryBO = new ShopCategoryBO();
            // 构造parent对象
            ShopCategoryBO parent = new ShopCategoryBO();
            parent.setShopCategoryId(shopDTO.getParentId());
            // 装载BO对象
            shopCategoryBO.setParent(parent);
        }
        // 二级类别
        if(shopDTO.getShopCategoryId() != null){
            // 一级类别为空时需实例化
            if(shopCategoryBO == null){
                shopCategoryBO = new ShopCategoryBO();
            }
            shopCategoryBO.setShopCategoryId(shopDTO.getShopCategoryId());
        }
        // 装载类别信息
        shopCondition.setShopCategoryBO(shopCategoryBO);

        // 区域信息
        if(shopDTO.getAreaId() != null && shopDTO.getAreaId() > 0 ){
            // 实例化对应的子对象
            AreaDO areaDO = new AreaDO();
            areaDO.setAreaId(shopDTO.getAreaId());
            shopCondition.setAreaDO(areaDO);
        }

        // 商铺名称，用于模糊查询
        if(shopDTO.getShopName() != null && Strings.isNotBlank(shopDTO.getShopName())){
            shopCondition.setShopName(shopDTO.getShopName());
        }

        // 启用状态
        if(shopDTO.getEnableStatus() != null){
            shopCondition.setEnableStatus(shopDTO.getEnableStatus());
        }

        // 负责人信息
        if(shopDTO.getOwnerId() != null){
            PersonInfoDO personInfoDO = new PersonInfoDO();
            personInfoDO.setUserId(shopDTO.getOwnerId());
            shopCondition.setOwnerInfo(personInfoDO);
        }
        return shopCondition;
    }

}
