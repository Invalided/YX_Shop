package com.o2o.shop.config;

import com.o2o.shop.interceptor.AdminAuthInterceptor;
import com.o2o.shop.interceptor.ManagerAuthInterceptor;
import com.o2o.shop.interceptor.UserAuthInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author 勿忘初心
 * @since 2023-07-25-17:23
 * 登录认证配置类,配置不同类型用户的限制路径
 */
@Configuration
public class AuthenticationConfig implements WebMvcConfigurer {

    /**
     * 为不同的模块注册拦截路径
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 普通用户,涉及到购物车,个人中心
        registry.addInterceptor(new UserAuthInterceptor())
                // 拦截所有以mall开头的路径
                .addPathPatterns("/mall/**")
                // 排除首页,分类页
                .excludePathPatterns("/mall/home","/mall/list","/mall/listCondition");
        // 商家,全路径拦截
        registry.addInterceptor(new ManagerAuthInterceptor())
                // 拦截所有以manger开头的路径
                .addPathPatterns("/manager/**")
                // 排除商铺列表页、商铺注册
                .excludePathPatterns("/manager/shop_list","/manager/shop_register");
        registry.addInterceptor(new AdminAuthInterceptor())
                // 拦截所有以admin开头的路径
                .addPathPatterns("/admin/**")
                // 排除admin登录接口
                .excludePathPatterns("/admin/auth/login");
    }

    /**
     * 静态资源放行处理
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 放行静态资源，允许访问静态资源，如CSS、JavaScript、图片等
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }
}
