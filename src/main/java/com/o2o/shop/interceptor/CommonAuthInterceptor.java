package com.o2o.shop.interceptor;

import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.model.PersonInfoDO;
import com.o2o.shop.vo.LocalAuthVO;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 勿忘初心
 * @since 2023-07-25-19:58
 * 公用的逻辑抽取,减少代码冗余
 */
public class CommonAuthInterceptor {

    /**
     * 校验用户权限类型,通过则返回用户信息
     * @param request 请求对象
     * @param userType 用户类型
     * @return
     */
    public LocalAuthVO checkUserRole(HttpServletRequest request, Integer userType){
        LocalAuthVO localAUthVO = (LocalAuthVO) request.getSession().getAttribute(GlobalConstant.USER_SESSION_INFO);
        if(localAUthVO != null ){
            // 获取具体的用户信息
            PersonInfoDO personInfo = localAUthVO.getPersonInfo();
            if(personInfo != null && personInfo.getUserType().equals(userType)){
                return localAUthVO;
            }
        }
        return null;
    }

    /**
     * 日志记录
     * @param request
     * @return
     */
    public StringBuilder accessRecord(HttpServletRequest request){
        // 记录相关信息到日志中
        StringBuilder accessInfo = new StringBuilder();
        // 获取用户ip
        accessInfo.append(request.getRemoteHost() + " | ");
        // 请求的路径
        accessInfo.append(request.getRequestURL() + " | ");
        // 用户所使用的客户端信息
        accessInfo.append(request.getHeader("user-agent"));
        return accessInfo;
    }

}
