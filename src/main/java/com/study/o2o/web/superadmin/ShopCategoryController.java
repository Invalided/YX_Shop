package com.study.o2o.web.superadmin;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.ConstantForSuperAdmin;
import com.study.o2o.dto.ShopCategoryExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.shopCategory;
import com.study.o2o.enums.shopCategoryStateEnum;
import com.study.o2o.service.shopCategoryService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
public class ShopCategoryController {
	@Autowired
	private shopCategoryService shopCategoryService;
	
	/**
	 * 列出店铺类别信息
	 * @return
	 */
	@RequestMapping(value = "/listshopcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listShopCategorys() {
		Map<String, Object> modelMap = new HashMap<>();
		List<shopCategory> list = new ArrayList<shopCategory>();
		try {
			// 获取所有一级店铺类别列表
			list = shopCategoryService.getShopCategoryList(null);
			// 获取所有二级店铺类别列表,并添加进以及店铺类别列表中
			list.addAll(shopCategoryService.getShopCategoryList(new shopCategory()));
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	
	/**
	 * 列出一级店铺类别
	 * @return
	 */
	@RequestMapping(value = "/list1stlevelshopcategorys",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> list1stLevelShopCategorys(){
		Map<String, Object> modelMap = new HashMap<>();
		List<shopCategory> list = new ArrayList<>();
		try {
			//获取所有一级店铺类别列表
			list = shopCategoryService.getShopCategoryList(null);
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}
	
	/**
	 * 列出二级店铺类别信息
	 * @return
	 */
	@RequestMapping(value = "/list2ndlevelshopcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> list2ndLevelShopCategorys() {
		Map<String, Object> modelMap = new HashMap<>();
		List<shopCategory> list = new ArrayList<>();
		try {
			// 获取所有的二级店铺列表
			list = shopCategoryService.getShopCategoryList(new shopCategory());
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	/**
	 * 添加店铺类别
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addshopcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addShopCategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		shopCategory shopCategory = null;
		// 接收并转化相应的参数，包括店铺类别信息以及图片信息
		String shopCategoryStr = httpServletRequestUtil.getString(request, "shopCategoryStr");
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			shopCategory = mapper.readValue(shopCategoryStr, shopCategory.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 过滤请求中的multi字样，拦截外部非图片流
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, "shopCategoryManagementAdd_shopCategoryImg");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 空值判断
		if (shopCategory != null && thumbnail != null) {
			try {
				// decode可能有中文的地方
				shopCategory.setShopCategoryName((shopCategory.getShopCategoryName() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryName(), "UTF-8")));
				shopCategory.setShopCategoryDesc((shopCategory.getShopCategoryDesc() == null) ? null
						: (URLDecoder.decode(shopCategory.getShopCategoryDesc(), "UTF-8")));
				// 添加店铺信息
				ShopCategoryExecution se = shopCategoryService.addShopCategory(shopCategory, thumbnail);
				if (se.getState() == shopCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺类别信息");
		}
		return modelMap;
	}
	/**
	 * 修改店铺类别信息
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyshopcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShopCategory(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		shopCategory shopCategory = null;
		// 接收并转化相应的参数，包括店铺类别信息以及图片信息
		String shopCategoryStr = httpServletRequestUtil.getString(request, "shopCategoryStr");
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			shopCategory = mapper.readValue(shopCategoryStr, shopCategory.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 过滤请求中的multi字样，拦截外部非图片流
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, "shopCategoryManagementEdit_shopCategoryImg");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		if(shopCategory != null && shopCategory.getShopCategoryId() != null) {
			//decode有中文的地方
			try {
				shopCategory.setShopCategoryName((shopCategory.getShopCategoryName() == null)?null
						:(URLDecoder.decode(shopCategory.getShopCategoryName(),"UTF-8")));
				shopCategory.setShopCategoryDesc((shopCategory.getShopCategoryDesc() == null)?null
						:(URLDecoder.decode(shopCategory.getShopCategoryDesc(),"UTF-8")));	
				ShopCategoryExecution se = shopCategoryService.modifyShopCategory(shopCategory, thumbnail);
				if(se.getState() == shopCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success",true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg","请输入店铺类别信息");
		}
		return modelMap;
	}

	@RequestMapping(value = "/removeshopcategory",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeHeadLine(Long shopCategoryId){
		Map<String, Object> modelMap = new HashMap<>();
		//空值判断
		if(shopCategoryId != null && shopCategoryId > 0) {
			try {
				//根据传入的Id删除对应的头条
				ShopCategoryExecution shopCategoryExecution = shopCategoryService.removeShopCategoryById(shopCategoryId);	
				if(shopCategoryExecution.getState() == shopCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", shopCategoryExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入类别Id信息");
		}
		return modelMap;
	}
	
	/**
	 * 图片预处理
	 * 
	 * @param request
	 * @param thumbnail
	 * @param flowName
	 * @return
	 * @throws IOException
	 */

	private imageHolder handleImage(HttpServletRequest request, imageHolder thumbnail, String flowName)
			throws IOException {
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		// 取出并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile(flowName);
		if (thumbnailFile != null) {
			thumbnail = new imageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		}
		return thumbnail;
	}
}
