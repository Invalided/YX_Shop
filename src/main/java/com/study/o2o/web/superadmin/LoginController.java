package com.study.o2o.web.superadmin;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.entity.localAuth;
import com.study.o2o.service.localAuthService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
@Api(description = "管理员登录")
public class LoginController {
	@Autowired
	private localAuthService localAuthService;
	
	/**
	 * 登录验证
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation("登录校验")
	@RequestMapping(value = "/logincheck",method = RequestMethod.POST)
	@ResponseBody
	//跨域请求问题 由于Vue.js的启动端口暂与SSM不同 应添加如下注解
	//@CrossOrigin
	private Map<String, Object> loginCheck(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取前端传递过来的账号和密码
		String userName = httpServletRequestUtil.getString(request, "userName");
		String passWord = httpServletRequestUtil.getString(request, "passWord");
		//空值判断
		if(userName != null && passWord != null) {
			//获取本地账号授权信息
			localAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, passWord);
			if(localAuth != null) {
				// 若账号密码正确,则验证用户的身份是否为超级管理员
				if(localAuth.getPersonInfo().getUserType() == 3) {
					modelMap.put("success", true);
					// 登录成功则设置session信息
					request.getSession().setAttribute("user", localAuth.getPersonInfo());
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "非管理员没有权限访问");
				}
			}else {
				modelMap.put("success",false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码不能为空");
		}
		return modelMap;
	}
}
