package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.UserShopBO;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.shopmanager.ManagePointDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.UserShopMapMapper;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.model.ShopDO;
import com.o2o.shop.model.UserShopMapDO;
import com.o2o.shop.service.UserShopMapService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ShopVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * @author 勿忘初心
 * @since 2023-07-17-18:11
 */
@Service
@Slf4j
public class UserShopMapServiceImpl implements UserShopMapService {

    @Autowired
    private UserShopMapMapper userShopMapMapper;

    @Autowired
    private HttpSession sessionOperator;


    @Override
    public UserShopMapDO queryPointInfoById(Integer userId, Integer shopId) {
        QueryWrapper queryWrapper = new QueryWrapper();
        queryWrapper.eq("user_id",userId);
        queryWrapper.eq("shop_id",shopId);

        UserShopMapDO userShopMapDO = userShopMapMapper.selectOne(queryWrapper);
        if(userShopMapDO == null){
            throw new BusinessException(ExceptionCodeEnum.EC60000);
        }
        return userShopMapDO;
    }

    @Override
    public PageVO queryUserPointListByCondition(ManagePointDTO managePointDTO, Integer pageNum, Integer pageSize) {
        if(managePointDTO.getShopId() == null){
            // 尝试从session中获取数据
            ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
            if(currentShop == null){
                log.error("获取积分列表失败，未能获取到商铺信息");
                throw new BusinessException(ExceptionCodeEnum.EC10004);
            }
            managePointDTO.setShopId(currentShop.getShopId());
        }
        // 默认不分页
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = -1;
        }
        // 分页
        Page page = new Page(pageNum,pageSize);
        // 条件查询拼接
        UserShopBO userShopCondition = handleUserShopCondition(managePointDTO);
        Page userShopMapPage = userShopMapMapper.queryShopMapPointPage(page,userShopCondition);
        if(userShopMapPage.getRecords().isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC60000);
        }
        return new PageVO(userShopMapPage.getRecords(),userShopMapPage.getRecords().size());
    }

    private UserShopBO handleUserShopCondition(ManagePointDTO managePointDTO) {
        UserShopBO userShopBO = new UserShopBO();
        ShopDO shopDO = new ShopDO();
        PersonInfoDO user = new PersonInfoDO();
        // 商铺id
        if(managePointDTO.getShopId() != null){
            shopDO.setShopId(managePointDTO.getShopId());
        }
        // 商铺名称
        if(managePointDTO.getShopName() != null){
            shopDO.setShopName(managePointDTO.getShopName());
        }
        userShopBO.setShop(shopDO);
        // 用户id
        if(managePointDTO.getUserId() != null){
            user.setUserId(managePointDTO.getUserId());
        }
        // 用户昵称
        if(managePointDTO.getUserName() != null){
            user.setName(managePointDTO.getShopName());
        }
        userShopBO.setUser(user);
        // 时间
        if(managePointDTO.getCreateTime() != null){
            userShopBO.setCreateTime(managePointDTO.getCreateTime());
        }
        return userShopBO;
    }

    @Override
    public int updatePointInfoByEntity(UserShopMapDO userShopMapDO) {
        return userShopMapMapper.updateById(userShopMapDO);
    }
}
