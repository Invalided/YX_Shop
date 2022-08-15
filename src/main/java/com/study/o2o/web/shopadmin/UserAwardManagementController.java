package com.study.o2o.web.shopadmin;

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

import com.study.o2o.dto.UserAwardMapExecution;
import com.study.o2o.entity.Award;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.UserAwardMap;
import com.study.o2o.service.UserAwardMapService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
@Api(description = "用户奖品管理")
public class UserAwardManagementController {
	
	@Autowired
	private UserAwardMapService userAwardMapService;

	@ApiOperation(value = "列出该店铺下用户的奖品信息")
	@RequestMapping(value="/listuserawardmapsbyshop",method=RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserAwardMapsByShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//从Session中获取对象
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = httpServletRequestUtil.getInt(request, "pageSize");
		//空值判断
		if(pageIndex>-1 && pageSize>-1 && currentShop!=null && currentShop.getShopId()!=null) {
			UserAwardMap userAwardMap = new UserAwardMap();
			userAwardMap.setShop(currentShop);
			//从请求中获取奖品名
			String awardName = httpServletRequestUtil.getString(request, "awardName");
			if(awardName!=null) {
				//添加条件 按照奖品名搜索
				Award award = new Award();
				award.setAwardName(awardName);
				userAwardMap.setAward(award);
			}
			//分页返回结果
			UserAwardMapExecution ue = userAwardMapService.listUserAwardMap(userAwardMap, pageIndex, pageSize);
			modelMap.put("userAwardMapList", ue.getUserAwardMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
}
