package com.o2o.shop.interceptor;

import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.ShopVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author 勿忘初心
 * @since 2023-07-25-17:27
 * 商家登录认证
 */
@Slf4j
public class ManagerAuthInterceptor extends CommonAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取商家信息,首先获取其用户信息，验证身份类型
        LocalAuthVO localAuth = checkUserRole(request, GlobalConstant.RoleType.MANAGER.getValue());
        if(localAuth != null){
            // 上层已经验证personInfo非空状态
            PersonInfoDO managerInfo = localAuth.getPersonInfo();
            // 由于为商家,因此需要验证其是否有店铺列表信息权限
            List<ShopVO> shopList = (List<ShopVO>) request.getSession().getAttribute(GlobalConstant.MANAGER_CONTAIN_SHOP);
            if(shopList != null){
                // 使用set代替List降低时间复杂度
                Set<Integer> shopIdSet = shopList.stream()
                        .map(ShopVO::getOwnerId)
                        .collect(Collectors.toSet());
                if(shopIdSet.contains(managerInfo.getUserId())){
                    return true;
                }
            }
        }
        log.error("商家权限校验失败 {} ", accessRecord(request).toString());
        // 提示需要重新登录
        throw new BusinessException(ExceptionCodeEnum.EC10004);
    }
}
