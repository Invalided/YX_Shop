package com.study.o2o.interceptor.shopadmin;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.study.o2o.entity.PersonInfo;

/**
 * 店家管理系统登录验证拦截器
 */
public class shopLoginInterceptor extends HandlerInterceptorAdapter{
	/**
	 * 主要做事前拦截，即用户操作发生前，改写preHandle里的逻辑，进行拦截
	 * 继承HandlerInterceptorAdapter类 此类实现了HandlerInterceptor接口
	 * 主要流程 通过spring-web.xml方法对请求的路径进行拦截 通过对应的拦截器类对其进行处理
	 * 取出系统中登录存放的session 获取用户信息判断是否具有权限进行下一步的操作  满足第一个interceptor后 将进入接下来的interceptor中
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		//从session中取出用户信息来
		Object userObject = request.getSession().getAttribute("user");
		if(userObject!=null) {
			//若用户信息不为空则将session里的用户信息转换成PersonInfo实体类对象
			PersonInfo user = (PersonInfo)userObject;
			//做空值判断，确保userId不为空并且该账号的可用状态为1，并且用户类型为店家
			if(user!=null && user.getUserId()!=null && user.getUserId()>0&&user.getEnableStatus()==1) {
				//若通过验证则返回true,拦截器返回true后,用户接下来的操作得以正常的使用
				return true;
			}
		}
		//若不满足登录验证，则直接跳转到登录页面
		PrintWriter out = response.getWriter();
		out.print("<html>");
		out.print("<script>");
		out.print("window.open ('"+request.getContextPath()+"/local/login?usertype=2','_self')");
		out.print("</script>");
		out.print("</html>");
		return false;
	}
}
