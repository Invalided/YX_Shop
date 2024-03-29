package com.study.o2o.web.local;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/local")
@Api(description = "前端页面路由")
public class localController {
	/**
	 * 绑定帐号页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/accountbind", method = RequestMethod.GET)
	private String accountbind() {
		return "local/accountbind";
	}
	/**
	 * 修改密码页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/changepsw", method = RequestMethod.GET)
	private String changepsw() {
		return "local/changepsw";
	}	
	/**
	 * 登录页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	private String login() {
		return "local/login";
	}
	/**
	 * 注册路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/register",method = RequestMethod.GET)
	private String register() {
		return "local/register";
	}
}
