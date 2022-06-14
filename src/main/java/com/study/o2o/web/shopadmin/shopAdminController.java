package com.study.o2o.web.shopadmin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@RequestMapping(value = "shopadmin",method = {RequestMethod.GET})
public class shopAdminController {
	//返回设置的访问路径
	@RequestMapping(value = "/shopoperation")
	public String shopOperation() {
		//店铺操作页面
		return "shop/shopoperation";
	}
	
	@RequestMapping(value = "/shoplist")
	public String shopList() {
		//店铺列表页面
		return "shop/shoplist";
	}
	
	@RequestMapping(value = "/shopmanagement")
	public String shopManagement() {
		//店铺管理页面
		return "shop/shopmanagement";
	}
	
	@RequestMapping(value = "/productcategorymanagement",method = {RequestMethod.GET})
	public String productCategoryManage() {
		//商品类别管理页面
		return "shop/productcategorymanagement";
	}
	@RequestMapping(value = "/productoperation")
	public String productOperation() {
		//商品添加/编辑页面
		return "shop/productoperation";
	}
	@RequestMapping(value = "/productmanagement")
	public String productManagement() {
		//商品管理页面
		return "shop/productmanagement";
	}
	@RequestMapping(value="/productbuycheck")
	private String productByCheck() {
		//转发至店铺消费记录的页面
		return "shop/productbuycheck";
	}
	@RequestMapping(value = "/awardmanagement")
	private String awardManagement() {
		//奖品管理页路由
		return "shop/awardmanagement";
	}
	@RequestMapping(value = "/awardoperation")
	private String awardEdit() {
		//奖品编辑页路由
		return "shop/awardoperation";
	}
	@RequestMapping(value="/usershopcheck")
	private String userShopCheck() {
		//店铺用户积分统计路由
		return "shop/usershopcheck";
	}
	@RequestMapping(value="/awarddelivercheck")
	private String awardDeliverCheck() {
		//店铺用户积分兑换路由
		return "shop/awarddelivercheck";
	}
}
