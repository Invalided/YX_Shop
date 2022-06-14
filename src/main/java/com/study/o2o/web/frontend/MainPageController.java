package com.study.o2o.web.frontend;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.entity.headLine;
import com.study.o2o.entity.shopCategory;
import com.study.o2o.service.headLineService;
import com.study.o2o.service.shopCategoryService;

@Controller
@RequestMapping("/frontend")
public class MainPageController {
	@Autowired
	private shopCategoryService shopCategoryService;
	@Autowired
	private headLineService headLineService;
	
	@RequestMapping(value = "/listmainpageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String,Object> listMainPageInfo(){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<shopCategory> shopCategoryList = new ArrayList<>();
		try {
			//获取一级店铺类别列表(即parentId为空的ShopCategory)
			shopCategoryList = shopCategoryService.getShopCategoryList(null);
			modelMap.put("shopCategoryList",shopCategoryList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		List<headLine> headLineList = new ArrayList<>();
		try {
			//获取状态为可用(1)的头条列表
			headLine headLineCondition = new headLine();
			headLineCondition.setEnableStatus(1);
			headLineList = headLineService.getHeadLineList(headLineCondition);
			modelMap.put("headLineList", headLineList);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		modelMap.put("success", true);
		return modelMap;
	}
	
}
