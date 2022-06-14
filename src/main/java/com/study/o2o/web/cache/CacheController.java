package com.study.o2o.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.o2o.service.CacheService;
import com.study.o2o.service.areaService;
import com.study.o2o.service.headLineService;
import com.study.o2o.service.shopCategoryService;

@Controller
public class CacheController {
	
	@Autowired
	private CacheService cacheService;
	@Autowired
	private areaService areaService;
	@Autowired
	private headLineService headLineService;
	@Autowired
	private shopCategoryService shopCategoryService;
	
	/**
	 * 清除区域信息相关的所有Redis缓存
	 * 
	 * @return
	 */
	@RequestMapping(value = "/clearcache4area",method = RequestMethod.GET)
	private String clearCache4Area() {
		cacheService.removeFromCache(areaService.AREALISTKEY);
		return "shop/operationsuccess";
	}
	
	@RequestMapping(value = "/clearcache4headline",method = RequestMethod.GET)
	private String clearCache4Headline() {
		cacheService.removeFromCache(headLineService.HLLISTKEY);
		return "shop/operationsuccess";
	}
	
	@RequestMapping(value = "/clearcache4shopcategory",method = RequestMethod.GET)
	private String clearCache4ShopCategory() {
		cacheService.removeFromCache(shopCategoryService.SCLISTKEY);
		return "shop/operationsuccess";
	}
}
