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
 * @since 2023-07-25-17:44
 * 管理员信息认证
 */
@Slf4j
public class AdminAuthInterceptor extends CommonAuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        LocalAuthVO localAuthVO = checkUserRole(request, GlobalConstant.RoleType.ADMIN.getValue());
        if(localAuthVO == null){
            // 记录相关信息到日志中
            log.error("管理员权限校验失败 {} ", accessRecord(request).toString());
            // 提示需要重新登录
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
       return true;
    }
}
