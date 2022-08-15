package com.study.o2o.web.superadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.ConstantForSuperAdmin;
import com.study.o2o.dto.shopExecution;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.shopCategory;
import com.study.o2o.enums.shopStateEnum;
import com.study.o2o.service.shopCategoryService;
import com.study.o2o.service.ShopService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
@Api(description = "商铺管理")
public class ShopController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private shopCategoryService shopCategoryService;
	
	/**
	 * 获取店铺列表
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取店铺列表")
	@RequestMapping(value = "/listshops",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listShops(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		shopExecution se = null;
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, ConstantForSuperAdmin.PAGE_NO);
		int	pageSize = httpServletRequestUtil.getInt(request, ConstantForSuperAdmin.PAGE_SIZE);
		//空值判断
		if(pageIndex > 0 && pageSize > 0) {
			Shop shopCondition  = new Shop();
			int enableStatus = httpServletRequestUtil.getInt(request, "enableStatus");
			if(enableStatus >= 0) {
				//若传入可用状态,则将可用状态添加到查询条件中
				shopCondition.setEnableStatus(enableStatus);
			}
			long shopCategoryId = httpServletRequestUtil.getLong(request, "shopCategoryId");
			if(shopCategoryId > 0) {
				//若传入店铺类别，则将店铺类别添加的到查询条件中
				shopCategory sc = new shopCategory();
				sc.setShopCategoryId(shopCategoryId);
				shopCondition.setShopCategory(sc);
			}
			String shopName = httpServletRequestUtil.getString(request, "shopName");
			if(shopName != null) {
				try {
					//若传入店铺名称，则将店铺名称解码后添加到查询条件中,进行模糊查询
					shopCondition.setShopName(URLDecoder.decode(shopName,"UTF-8"));
				}catch (Exception e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
				}
			}
			try {
				//根据查询条件分页返回店铺列表
				se = shopService.getShopList(shopCondition, pageIndex, pageSize);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
			if(se.getShopList() != null) {
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, se.getShopList());
				modelMap.put(ConstantForSuperAdmin.TOTAL,se.getCount());
				modelMap.put("success", true);
			}else {
				//取出数据为空，也返回new list 以使得前端不出错
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, new ArrayList<Shop>());
				modelMap.put(ConstantForSuperAdmin.TOTAL,0);
				modelMap.put("success",true);
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "空的查询信息");
			return modelMap;
		}
	}
	/**
	 * 根据id返回店铺信息
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "根据id返回店铺信息")
	@RequestMapping(value = "/searchshopbyid",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> searchShopById(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		Shop shop = null;
		//从请求中取出店铺id
		long shopId = httpServletRequestUtil.getLong(request, "shopId");
		if(shopId > 0) {
			try {
				// 根据Id获取店铺实例
				shop = shopService.getByShopId(shopId);
			} catch (Exception e) {
				modelMap.put("success",false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
			if(shop != null) {
				List<Shop> shopList = new ArrayList<>();
				shopList.add(shop);
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE,shopList);
				modelMap.put(ConstantForSuperAdmin.TOTAL, 1);
				modelMap.put("success",true);
			}else {
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE , new ArrayList<>());
				modelMap.put(ConstantForSuperAdmin.TOTAL,0);
				modelMap.put("success", true);
			}
			return modelMap;
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "空的查询信息");
			return modelMap;
		}
	}

	/**
	 * 修改商铺信息
	 * @param shopStr
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "修改商铺信息")
	@RequestMapping(value = "/modifyshop",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(String shopStr,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			// 获取前端传递过来的shop json字符串，将其转换为shop实例
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//空值判断
		if(shop != null && shop.getShopId() != null) {
			try {
				// 若前端传来需要修改的字段，则设置上，否则设置为空，为空则不修改
				shop.setShopName(
						(shop.getShopName() == null)?null:(URLDecoder.decode(shop.getShopName(),"UTF-8")));
				shop.setShopDesc(
						(shop.getShopDesc() == null)?null:(URLDecoder.decode(shop.getShopDesc(),"UTF-8")));
				shop.setShopAddr(
						(shop.getShopAddr() == null)?null:(URLDecoder.decode(shop.getShopAddr(),"UTF-8")));
				shop.setAdvice(((shop.getAdvice() == null)||shop.getAdvice().equals("\"\""))?null:(URLDecoder.decode(shop.getAdvice(),"UTF-8")));
				if(shop.getShopCategory() != null && shop.getShopCategory().getShopCategoryId() != null) {
					//若需要修改店铺类别,则先获取店铺类别
					shopCategory sc = shopCategoryService.getShopCategoryById(shop.getShopCategory().getShopCategoryId());
					shop.setShopCategory(sc);
				}
				//修改店铺信息
				shopExecution se = shopService.modifyShop(shop, null);
				if(se.getState() == shopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg","请输入店铺信息");
		}
		return modelMap;
	}
}
