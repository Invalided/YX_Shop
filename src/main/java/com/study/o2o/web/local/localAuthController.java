package com.study.o2o.web.local;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.dto.localAuthExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.localAuth;
import com.study.o2o.enums.localAuthStateEnum;
import com.study.o2o.exceptions.localAuthOperationExcetpion;
import com.study.o2o.service.localAuthService;
import com.study.o2o.util.codeUtil;
import com.study.o2o.util.httpServletRequestUtil;

@Controller 
@RequestMapping(value = "local", method = {RequestMethod.GET,RequestMethod.POST})
public class localAuthController {
	@Autowired
	private localAuthService localAuthService;
	
	/**
	 * 将用户信息与平台账号绑定
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/bindlocalauth",method = RequestMethod.GET)
	@ResponseBody	
	private Map<String, Object> bindLocalAuth(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码校验
		if(!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//获取输入的账号
		String userName = httpServletRequestUtil.getString(request, "userName");
		//获取输入的密码
		String password = httpServletRequestUtil.getString(request, "password");
		//从session中获当前用户信息(用户一旦通过微信登录之后,便能取到用户的信息)
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		//非空判断,要求账号密码以及当前的用户session非空
		if(userName != null && password != null && user != null && user.getUserId() != null) {
			//创建localAuth对象并赋值
			localAuth localAuth = new localAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			//绑定账号
			localAuthExecution le = localAuthService.bindLocalAuth(localAuth);
			if(le.getState() == localAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("succces", false);
				modelMap.put("errMsg", le.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户不存在或用户不能为空");
		}
		return modelMap;
	}
	/**
	 * 修改密码
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/changelocalpwd",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> changeLocalPwd(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//验证码
		if(!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg","输入了错误的验证码");
		}
		//获取账号
		String userName = httpServletRequestUtil.getString(request, "userName");
		//获取原密码
		String password = httpServletRequestUtil.getString(request, "password");
		//获取新密码
		String newPassword = httpServletRequestUtil.getString(request, "newPassword");
		//从session中获取当前用户信息(用户一旦通过微信登录之后, 便能获取到用户的信息)
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		//非空判断, 要求账号新旧密码以及当前的用户session非空, 且新旧密码不相同
		if(userName != null && password != null && newPassword != null && user != null && user.getUserId() != null
				&& ! password.equals(newPassword)) {
			try {
				//查看原先账号，看看输入的账号是否一致，不一致则认为是非法操作
				localAuth localAuth = localAuthService.getLocalAuthByUserId(user.getUserId());
				if(localAuth == null || !localAuth.getUsername().equals(userName)) {
					//不一致则直接退出
					modelMap.put("success", false);
					modelMap.put("errMsg", "输入的的账号非本次登录的账号");
					return modelMap;
				}
				//修改平台账号的用户密码
				localAuthExecution le = localAuthService.modifyLocalAuth(user.getUserId(), userName, password, newPassword);
				if(le.getState() == localAuthStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", le.getStateInfo());
				}
			}catch (localAuthOperationExcetpion e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else{
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入密码");
		}
		return modelMap;
	}
	
	/**
	 * 登陆验证
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/logincheck", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> logincheck(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 获取是否需要进行验证码校验的标识符
		boolean needVerify = httpServletRequestUtil.getBoolean(request, "needVerify");
		if (needVerify && !codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 获取输入的帐号
		String userName = httpServletRequestUtil.getString(request, "userName");
		// 获取输入的密码
		String password = httpServletRequestUtil.getString(request, "password");
		// 非空校验
		if (userName != null && password != null) {
			// 传入帐号和密码去获取平台帐号信息
			localAuth localAuth = localAuthService.getLocalAuthByUsernameAndPwd(userName, password);
			if (localAuth != null) {
				if(localAuth.getPersonInfo().getEnableStatus() == 0) {
					modelMap.put("success", false);
					modelMap.put("errMsg", "该帐号被禁止登录");
					return  modelMap;
				}
				// 若能取到帐号信息则登录成功
				modelMap.put("success", true);
				// 05.01 添加用户类型 方便登录使用
				modelMap.put("usertype", localAuth.getPersonInfo().getUserType());
				// 05.01 添加用户id 
				modelMap.put("id", localAuth.getPersonInfo().getUserId());
				// 05.25 添加用户名
				modelMap.put("uname",localAuth.getPersonInfo().getName());
				// 同时在session里设置用户信息
				request.getSession().setAttribute("user", localAuth.getPersonInfo());
				// 根据账号类型设置 商家session
				if(localAuth.getPersonInfo().getUserType() == 2) {
					request.getSession().setAttribute("shop", localAuth.getPersonInfo());
				}
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", "用户名或密码错误");
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户名或密码不能为空");
		}
		return modelMap;
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST)
	@ResponseBody
	/**
	 * 当用户点击登出按钮的时候注销session
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	private Map<String, Object> logout(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 将用户session置为空
		request.getSession().setAttribute("user", null);
		modelMap.put("success", true);
		return modelMap;
	}
	/**
	 * 用户账号注册
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/registerlocalauth",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> registerLocalAuth(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码校验
		if(!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//获取输入的账号
		String userName = httpServletRequestUtil.getString(request, "userName");
		//获取输入的密码
		String password = httpServletRequestUtil.getString(request, "password");
		//获取用户类型 默认为1 顾客
		Integer userType = httpServletRequestUtil.getInt(request, "usertype");
		//使用用户名先向person_info表写入数据 再使用localAuth创建用户信息 目前仅仅开放用户注册
		PersonInfo user = new PersonInfo();
		//非空判断,要求账号密码为非空
		if(userName != null && password != null) {
			//初始化personinfo数据
			user.setName(userName);
			user.setGender("1");
			user.setEnableStatus(1);
			//默认设置用户权限为 1
			user.setUserType(1);
			user.setCreateTime(new Date());
			user.setLastEditTime(new Date());
			//创建localAuth对象并赋值
			localAuth localAuth = new localAuth();
			localAuth.setUsername(userName);
			localAuth.setPassword(password);
			localAuth.setPersonInfo(user);
			//注册账号
			localAuthExecution le = localAuthService.registerLocalAuth(localAuth);
			if(le.getState() == localAuthStateEnum.SUCCESS.getState()) {
				modelMap.put("success", true);
			}else {
				modelMap.put("succces", false);
				modelMap.put("errMsg", le.getStateInfo());
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "用户不存在或用户不能为空");
		}
		return modelMap;
	}
}
