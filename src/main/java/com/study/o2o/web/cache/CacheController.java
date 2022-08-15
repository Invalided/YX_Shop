package com.study.o2o.web.cache;

import com.study.o2o.service.CacheService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


import javax.annotation.Resource;

import static com.study.o2o.service.areaService.AREALISTKEY;
import static com.study.o2o.service.headLineService.HLLISTKEY;
import static com.study.o2o.service.shopCategoryService.SCLISTKEY;


/**
 * @author 勿忘初心
 */

@Controller
@Api(description = "缓存管理")
public class CacheController {
	@Autowired
	private CacheService cacheService;

	/**
	 * 清除区域信息相关的所有Redis缓存
	 * 
	 * @return 操作成功
	 */
	@ApiOperation(value = "清除区域信息缓存")
	@RequestMapping(value = "/clearcache4area",method = RequestMethod.GET)
	private String clearCache4Area() {
		cacheService.removeFromCache(AREALISTKEY);
		return "shop/operationsuccess";
	}
	@ApiOperation(value = "清除头条缓存")
	@RequestMapping(value = "/clearcache4headline",method = RequestMethod.GET)
	private String clearCache4Headline() {
		cacheService.removeFromCache(HLLISTKEY);
		return "shop/operationsuccess";
	}
	@ApiOperation(value = "清除商铺分类缓存")
	@RequestMapping(value = "/clearcache4shopcategory",method = RequestMethod.GET)
	private String clearCache4ShopCategory() {
		cacheService.removeFromCache(SCLISTKEY);
		return "shop/operationsuccess";
	}
}
