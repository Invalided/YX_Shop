package com.study.o2o.web.frontend;

//import javax.security.auth.message.callback.PrivateKeyCallback.Request;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping("/frontend")
public class frontendController {
	/**
	 * 首页
	 * @return
	 */
	@RequestMapping(value = "/index",method = RequestMethod.GET)
	private String index() {
		return "frontend/index";
	}
	
	/**
	 * 商铺详情页路由
	 * @return
	 */
	@RequestMapping(value = "/shopdetail",method = RequestMethod.GET)
	private String showShopDetail() {
		return "frontend/shopdetail";
	}
	
	/**
	 * 商品列表页路由
	 * @return
	 */
	@RequestMapping(value = "/shoplist",method = RequestMethod.GET)
	private String showShopList() {
		return "frontend/shoplist";
	}
	
	/**
	 * 商品详情页路由
	 * @return
	 */
	@RequestMapping(value = "/productdetail",method = RequestMethod.GET)
	private String showProductDetail() {
		return "frontend/productdetail";
	}
	/**
	 * 奖品详情页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myawarddetail", method = RequestMethod.GET)
	private String showMyAwardDetail() {
		return "frontend/myawarddetail";
	}
	
	/**
	 * 购物车页路由
	 * @return
	 */
	@RequestMapping(value = "/shopcart",method = RequestMethod.GET)
	private String showShopCart() {
		return "frontend/shopcart";
	}
	
	/**
	 * 消费记录列表页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/myrecord", method = RequestMethod.GET)
	private String showMyRecord() {
		return "frontend/myrecord";
	}
	/**
	 * 用户个店铺积分信息页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/mypoint", method = RequestMethod.GET)
	private String showMyPoint() {
		return "frontend/mypoint";
	}
	
	/**
	 * 奖品预览页路由
	 * 
	 * @return
	 */
	@RequestMapping(value = "/awarddetail")
	private String showAwardDetail() {
		return "frontend/awarddetail";
	}
	
	@RequestMapping(value = "/food",method = RequestMethod.GET)
	private String showFood() {
		return "frontend/food";
	}
	
	@RequestMapping(value = "/package",method = RequestMethod.GET)
	private String showPackage() {
		return "frontend/package";
	}
	@RequestMapping(value = "/awardlist",method = RequestMethod.GET)
	private String showAwardList() {
		return "frontend/awardlist";
	}
	@RequestMapping(value = "/pointrecord",method = RequestMethod.GET)
	private String showPointRecord() {
		return "frontend/pointrecord";
	}
	/**
	 * 05.25 添加
	 * 个人中心页面
	 */
	@RequestMapping(value = "/user",method = RequestMethod.GET)
	private String showUserPage() {
		return "frontend/user";
	}
	
	@RequestMapping(value = "/confirmpay",method = RequestMethod.GET)
	private String confirmPay() {
		return "frontend/confirmpay";
	}
	
	@RequestMapping(value = "/paysuccess",method = RequestMethod.GET)
	private String paySuccess() {
		return "frontend/paysuccess";
	}
	
	@RequestMapping(value = "/orders",method = RequestMethod.GET)
	private String ordersPage() {
		return "frontend/orders";
	}
}
