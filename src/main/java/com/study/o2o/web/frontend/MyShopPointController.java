package com.study.o2o.web.frontend;

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

import com.study.o2o.dto.UserShopMapExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.UserShopMap;
import com.study.o2o.service.UserShopMapService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@Api(description = "商品积分")
public class MyShopPointController {
	@Autowired
	private UserShopMapService userShopMapService;
	
	/**
	 * 列出用户的积分情况
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "查询用户的积分情况")
	@RequestMapping(value = "/listusershopmapsbycustomer",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserShopMapsByCustomer(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = httpServletRequestUtil.getInt(request, "pageSize");
		//从Session中获取信息
		PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
		//空值判断
		if(pageIndex > -1 && pageSize > -1 && user != null  && user.getUserId() != null) {
			UserShopMap userShopMapCondition = new UserShopMap();
			userShopMapCondition.setUser(user);
			long shopId = httpServletRequestUtil.getLong(request, "shopId");
			if(shopId > -1) {
				//若传入的店铺Id不为空，则取出该店铺该顾客的积分情况
				Shop shop = new Shop();
				shop.setShopId(shopId);
				userShopMapCondition.setShop(shop);
			}
			//根据查询条件获取顾客的个店铺积分情况
			UserShopMapExecution ue = userShopMapService.listUserShopMap(userShopMapCondition, pageIndex, pageSize);
			modelMap.put("userShopMapList", ue.getUserShopMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or ShopId");
		}
		return modelMap;
	}
}
