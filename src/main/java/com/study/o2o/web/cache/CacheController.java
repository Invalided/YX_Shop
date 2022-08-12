package com.study.o2o.web.cache;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.study.o2o.service.CacheService;
import static com.study.o2o.service.areaService.AREALISTKEY;
import static com.study.o2o.service.headLineService.HLLISTKEY;
import static com.study.o2o.service.shopCategoryService.SCLISTKEY;




@Controller
public class CacheController {

	private CacheService cacheService;

	@Autowired
	public void setCacheService(CacheService cacheService) {
		this.cacheService = cacheService;
	}

	/**
	 * 清除区域信息相关的所有Redis缓存
	 * 
	 * @return 操作成功
	 */
	@RequestMapping(value = "/clearcache4area",method = RequestMethod.GET)
	private String clearCache4Area() {
		cacheService.removeFromCache(AREALISTKEY);
		return "shop/operationsuccess";
	}
	
	@RequestMapping(value = "/clearcache4headline",method = RequestMethod.GET)
	private String clearCache4Headline() {
		cacheService.removeFromCache(HLLISTKEY);
		return "shop/operationsuccess";
	}
	
	@RequestMapping(value = "/clearcache4shopcategory",method = RequestMethod.GET)
	private String clearCache4ShopCategory() {
		cacheService.removeFromCache(SCLISTKEY);
		return "shop/operationsuccess";
	}
}
