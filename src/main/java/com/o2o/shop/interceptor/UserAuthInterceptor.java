package com.o2o.shop.interceptor;

import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.vo.LocalAuthVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author 勿忘初心
 * @since 2023-07-25-16:38
 * 顾客登录验证
 */
@Slf4j
public class UserAuthInterceptor extends CommonAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 获取用户信息,注意此处不能使用localAuthService获取数据,Interceptor并非Spring的Bean容易引发空指针
        LocalAuthVO localAuthVO = checkUserRole(request, GlobalConstant.RoleType.USER.getValue());
        if(localAuthVO == null){
            log.error("用户权限校验失败 {} ", accessRecord(request).toString());
            // 提示需要重新登录
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        return true;
    }
}
