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

import com.study.o2o.dto.UserProductMapExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.Product;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.UserProductMap;
import com.study.o2o.service.PersonInfoService;
import com.study.o2o.service.UserProductMapService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
@Api(description = "个人商品记录")
public class MyProductController {
	@Autowired
	private UserProductMapService userProductMapService;
	/**
	 * 列出某个顾客的商品消费信息
	 * 
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "列出某个顾客的商品消费信息" )
	@RequestMapping(value = "/listuserproductmapsbycustomer", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByCustomer(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = httpServletRequestUtil.getInt(request, "pageSize");
		//05.27获取用户uid set实体属性
		Long uid = httpServletRequestUtil.getLong(request, "uid");
		String uname = httpServletRequestUtil.getString(request, "uname");
		System.out.println("uid"+uid+"uname"+uname);
		//从Session中获取顾客信息
		PersonInfo user = (PersonInfo) request.getSession().getAttribute("user");
		PersonInfo tempinfo = user;
		System.out.println(user.toString());
		if(uid!=null && uname != null) {
			user.setUserId(uid);
			user.setName(uname);
			user.setUserType(1);
			//PersonInfo personInfo = personInfoService.getPersonInfoById(uid);
			//user.setName(personInfo.getName());
		}
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(user!=null)&&(user.getUserId()!=-1)) {
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setUser(user);
			long shopId = httpServletRequestUtil.getLong(request, "shopId");
			if(shopId > -1) {
				//若传入店铺信息，则列出某个店铺下该顾客的消费历史
				Shop shop = new Shop();
				shop.setShopId(shopId);
				userProductMapCondition.setShop(shop);
			}
			String productName = httpServletRequestUtil.getString(request, "productName");
			if(productName!=null) {
				//若传入的商品名不为空， 则按照商品名模糊查询
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
			//根据查询条件分页返回用户消费信息
			UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex, pageSize);
			modelMap.put("userProductMapList", ue.getUserProductMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId or user");
		}
		//设置session
		request.getSession().setAttribute("user", tempinfo);
		return modelMap;
	}
}
