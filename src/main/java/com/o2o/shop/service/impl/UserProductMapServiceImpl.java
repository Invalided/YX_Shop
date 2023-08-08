package com.o2o.shop.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.o2o.shop.bo.UserProductBO;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.mall.UserConsumptionDTO;
import com.o2o.shop.dto.mall.UserProductDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.mapper.UserProductMapMapper;
import com.o2o.shop.mapper.UserShopMapMapper;
import com.o2o.shop.model.*;
import com.o2o.shop.service.ProductService;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.service.UserProductMapService;
import com.o2o.shop.service.UserShopMapService;
import com.o2o.shop.vo.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpSession;
import java.math.BigDecimal;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-16-15:33
 */
@Service
@Slf4j
public class UserProductMapServiceImpl implements UserProductMapService {

    @Autowired
    private ShopService shopService;

    @Autowired
    private ProductService productService;

    @Autowired
    private UserProductMapMapper userProductMapMapper;

    @Autowired
    private UserShopMapService userShopMapService;

    @Autowired
    private UserShopMapMapper userShopMapMapper;

    @Autowired
    private HttpSession sessionOperator;

    @Override
    @Transactional
    public ResultDataVO createUserConsumption(List<UserConsumptionDTO> consumptionList) {
        // 校验用户信息
        LocalAuthVO userInfo = (LocalAuthVO) sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
        // 用户请求的userId
        Set<Integer> userIdSet = consumptionList.stream()
                .map(UserConsumptionDTO::getUserId)
                .collect(Collectors.toSet());
        // 用户类型是否匹配
        if(!userInfo.getPersonInfo().getUserType().equals(GlobalConstant.RoleType.USER.getValue())
                || !userIdSet.contains(userInfo.getPersonInfo().getUserId())
                || userIdSet.size() != 1){
            log.error("无法创建订单,用户类型错误或用户Id与Session中信息不符");
            throw new BusinessException(ExceptionCodeEnum.EC50000);
        }
        // 取出List中的数据，进行组合,userId,shopId,productId,nums;
        List<UserProductMapDO> userProductMapDOList = handleUserConsumptionInfo(consumptionList);
        if(userProductMapDOList.isEmpty()){
            log.error("订单信息列表为空");
            throw new BusinessException(ExceptionCodeEnum.EC50005);
        }
        for (UserProductMapDO userProductMapDO : userProductMapDOList) {
            // 创建消费记录
            int effectNum = userProductMapMapper.insert(userProductMapDO);
            if(effectNum < 1){
                log.error("创建用户订单信息失败" );
                throw new BusinessException(ExceptionCodeEnum.EC50000);
            }
            // 消费积分累计
            handleUserTradePoint(userProductMapDO);
        }

        return ResultDataVO.success(null);
    }

    @Override
    public PageVO queryUserOrderInfoByCondition(UserProductDTO userProductDTO, Integer pageNum, Integer pageSize) {
        // 尝试获取shopId
        ShopVO currentShop = (ShopVO) sessionOperator.getAttribute(GlobalConstant.CURRENT_SHOP);
        // 获取当前用户信息，进行校验
        LocalAuthVO userInfo = (LocalAuthVO) sessionOperator.getAttribute(GlobalConstant.USER_SESSION_INFO);
        // 若为商家,则通过当前的Session获取商铺信息
        if(userProductDTO.getShopId() == null){
            if(currentShop == null){
                log.error("无法获取消费记录,商家未登录");
                throw new BusinessException(ExceptionCodeEnum.EC20005);
            }
            userProductDTO.setShopId(currentShop.getShopId());
        }else{
            // 用户则需要验证请求的userId和Session中的是否一致
            if(!userInfo.getPersonInfo().getUserId().equals(userProductDTO.getUserId())
                    || !userInfo.getPersonInfo().getUserType().equals(GlobalConstant.RoleType.USER.getValue())){
                log.error("无法获取消费记录,用户id与session不一致");
                throw new BusinessException(ExceptionCodeEnum.EC10004);
            }
        }

        // 条件查询构建
        UserProductBO userProductBO = getUserProductCondition(userProductDTO);

        // 分页条件,默认不分页
        if(pageNum == null){
            pageNum = 1;
        }
        if(pageSize == null){
            pageSize = -1;
        }

        Page page = new Page(pageNum,pageSize);
        // 条件查询
        Page<UserProductMapVO> userProductList = userProductMapMapper.queryProductOrderPage(page, userProductBO);
        if(userProductList.getRecords().isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC50007);
        }
        // 转为PageVO对象
        return new PageVO(userProductList.getRecords(),userProductList.getRecords().size());
    }

    /**
     * 处理用户商品订单信息
     * @param consumptionList
     * @return
     */
    List<UserProductMapDO> handleUserConsumptionInfo(List<UserConsumptionDTO> consumptionList){
        List<UserProductMapDO> userProductMapInfo = consumptionList.stream()
                .map(userConsumption -> {
                // 根据商铺id获取ownerId
                ShopVO shopVO = shopService.queryShopById(userConsumption.getShopId());
                if(shopVO == null){
                    log.error("创建订单失败,商铺信息不存在");
                    throw new BusinessException(ExceptionCodeEnum.EC40000);
                }
                Integer ownerId = shopVO.getOwnerId();
                // 获取商品的积分信息
                ProductVO productVO = productService.queryProductById(userConsumption.getProductId());
                if(productVO == null){
                    log.error("创建订单失败,商品信息不存在");
                    throw new BusinessException(ExceptionCodeEnum.EC30000);
                }
                // 验证商品和商铺信息的一致性
                if(!(productVO.getShop().getShopId()).equals(shopVO.getShopId())){
                    log.error("创建订单失败，商品信息与商铺信息不一致");
                    throw new BusinessException(ExceptionCodeEnum.EC50010);
                }
                Integer productPoint = productVO.getPoint();

                String tradePrice = StringUtils.isNotBlank(productVO.getPromotionPrice())?
                        productVO.getPromotionPrice() : productVO.getNormalPrice();
                BigDecimal bigDecimal = new BigDecimal(tradePrice);
                if(bigDecimal.compareTo(BigDecimal.ZERO) == 0){
                    log.error("商铺id为{}的商品价格异常,不能为0");
                    throw new BusinessException(ExceptionCodeEnum.EC50010);
                }
                UserProductMapDO userProductMapDO = new UserProductMapDO();
                // 复制属性
                BeanUtils.copyProperties(userConsumption, userProductMapDO);
                // 设置对应字段
                userProductMapDO.setOperatorId(ownerId);
                userProductMapDO.setPoint(productPoint);
                userProductMapDO.setTradePrice(tradePrice);
                return userProductMapDO;
        }).collect(Collectors.toList());
        return userProductMapInfo;
    }

    /**
     * 用户消费积分处理
     * @param userProductMapDO
     */
    private void handleUserTradePoint(UserProductMapDO userProductMapDO){
        // 本次消费是否有积分
        if(userProductMapDO.getPoint() > 0){
            UserShopMapDO userShopInfo = null;
            // 计算本次积分，当前按商品数量*积分规则
            int pointCount = userProductMapDO.getPoint() * userProductMapDO.getNums();
            // 查看当前用户是否存在积分信息
            try{
                // 若无积分记录则会抛出异常
                userShopInfo  = userShopMapService.queryPointInfoById(userProductMapDO.getUserId(),
                        userProductMapDO.getShopId());
            }catch (BusinessException businessException){
                // 创建积分信息
                userShopInfo = UserShopMapDO.builder()
                        .userId(userProductMapDO.getUserId())
                        .shopId(userProductMapDO.getShopId())
                        .point(pointCount)
                        .build();
                int insertNum = userShopMapMapper.insert(userShopInfo);
                if(insertNum < 1){
                    log.error("新增积分信息操作失败");
                    throw new BusinessException(ExceptionCodeEnum.EC10010);
                }
                return;
            }
            // 更新该用户在对应店铺的积分
            userShopInfo.setPoint(userShopInfo.getPoint() + pointCount);
            int updateNum = userShopMapService.updatePointInfoByEntity(userShopInfo);
            if(updateNum < 1){
                    log.error("更新积分信息操作失败");
                    throw new BusinessException(ExceptionCodeEnum.EC10010);
            }
        }
    }

    /**
     * 条件查询封装
     * @param userProductDTO DTO对象
     * @return
     */
    public UserProductBO getUserProductCondition(UserProductDTO userProductDTO){

        UserProductBO userProductBO = null;
        // 用户信息对象
        PersonInfoDO user = null;
        // 用户Id查询
        if(userProductDTO.getUserId() != null){
           user = new PersonInfoDO();
           user.setUserId(userProductDTO.getUserId());
        }
        // 用户名称模糊查询
        if(userProductDTO.getUserName() != null && StringUtils.isNotBlank(userProductDTO.getUserName())){
            if(user == null){
                user = new PersonInfoDO();
            }
            user.setName(userProductDTO.getUserName());
        }
        // 店铺id查询
        ShopDO shop = null;
        if(userProductDTO.getShopId() != null){
            shop = new ShopDO();
            shop.setShopId(userProductDTO.getShopId());
        }
        ProductDO product = null;
        // 商品名模糊查询
        if(userProductDTO.getProductName() != null && StringUtils.isNotBlank(userProductDTO.getUserName())){
            product = new ProductDO();
            product.setProductName(userProductDTO.getProductName());
        }
        // 创建对象
        userProductBO = new UserProductBO();
        // 按时间查询
        if(userProductDTO.getCreateTime() != null){
            // 时间戳转换
            userProductBO.setCreateTime(userProductDTO.getCreateTime());
        }

        userProductBO.setUser(user);
        userProductBO.setShop(shop);
        userProductBO.setProduct(product);

        return userProductBO;
    }
}
