package com.study.o2o.web.shopadmin;

import java.io.IOException;
import java.util.HashMap;
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
import com.study.o2o.dto.AwardExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.Award;
import com.study.o2o.entity.Shop;
import com.study.o2o.enums.AwardStateEnum;
import com.study.o2o.service.AwardService;
import com.study.o2o.util.codeUtil;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class AwardManagementController {
	@Autowired
	private AwardService awardService;
	
	/**
	 * 通过店铺Id获取该店铺下的奖品列表
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listawardsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listAwardByShop(HttpServletRequest request){
		Map<String , Object> modelMap = new HashMap<>();
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize  = httpServletRequestUtil.getInt(request, "pageSize");
		//从session中获取shopId
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//空值校验
		if(pageIndex >-1 && pageSize > -1 && currentShop != null && currentShop.getShopId() !=null) {
			//判断查询条件里面是否有传入奖品名，有则模糊查询
			String awardName = httpServletRequestUtil.getString(request, "awardName");
			//拼接查询条件
			Award awardCondition = compactAwardCondition4Search(currentShop.getShopId(), awardName);
			//根据查询条件分页获取奖品列表即总数
			AwardExecution ae = awardService.getAwardList(awardCondition, pageIndex, pageSize);
			modelMap.put("awardList", ae.getAwardList());
			modelMap.put("count", ae.getCount());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	/**
	 * 通过商品id获取奖品信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getawardbyid",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getAwardById(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//从request中取出前端传递过来的awardId
		long awardId = httpServletRequestUtil.getLong(request, "awardId");
		//空值判断
		if(awardId>-1) {
			//根据传入的Id获取奖品信息并返回
			Award award = awardService.getAwardById(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty awardId");
		}
		return modelMap;
	}
	/**
	 * 添加奖品
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addaward",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addAward(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//验证码校验
		if(!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数变量的初始化，包括奖品，缩略图
		ObjectMapper mapper = new ObjectMapper();
		Award award = null;
		String awardStr = httpServletRequestUtil.getString(request, "awardStr");
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		//请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理，
		//里面有缩略图的空值判断
		try {
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request,thumbnail);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		try {
			//将前端传入的awardStr转换为奖品对象
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//空值判断
		if(award != null && thumbnail != null) {
			try {
				//从session中获取当前店铺的Id并赋值给award，减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				award.setShopId(currentShop.getShopId());
				//添加award
				AwardExecution ae = awardService.addAward(award, thumbnail);
				if(ae.getState() == AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入奖品信息");
		}
		return modelMap;
	}
	/**
	 * 修改奖品信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyaward",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyAward(HttpServletRequest request){
		boolean statusChange = httpServletRequestUtil.getBoolean(request, "statusChange");
		Map<String, Object> modelMap = new HashMap<>();
		//根据传入的状态值决定是否跳过验证码校验
		if(!statusChange && !codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg","输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数的变化量的初始化，包括商品，缩略图
		ObjectMapper mapper = new ObjectMapper();
		Award award = null;
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		// 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理，
		// 里边有缩略图的空值判断，请放心使用
		try {
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail);
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.toString());
			return modelMap;
		}
		try {
			String awardStr = httpServletRequestUtil.getString(request, "awardStr");
			//尝试获取前端传过来的表单String流并将其转换成award实体类
			award = mapper.readValue(awardStr, Award.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//空值判断
		if(award != null) {
			try {
				//从session中获取当前店铺的Id并赋值给award,减少对前端数据的依赖
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				award.setShopId(currentShop.getShopId());
				AwardExecution ae = awardService.modifyAward(award, thumbnail);
				if(ae.getState() == AwardStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success",false);
					modelMap.put("errMsg", ae.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg",e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
	/**
	 * 封装商品查询条件到award实例中
	 * 
	 * @param shopId
	 * @param awardName
	 * @return
	 */
	private Award compactAwardCondition4Search(long shopId,String awardName) {
		Award awardCondition = new Award();
		awardCondition.setShopId(shopId);
		if (awardName != null) {
			awardCondition.setAwardName(awardName);
		}
		return awardCondition;
	}
	/**
	 * 图片预处理
	 * 
	 * @param request
	 * @param thumbnail
	 * @return
	 * @throws IOException
	 */
	private imageHolder handleImage(HttpServletRequest request,imageHolder thumbnail) throws IOException{
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request;
		//取出缩略图并构建ImageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		if(thumbnailFile !=null) {
			thumbnail = new imageHolder(thumbnailFile.getOriginalFilename(),thumbnailFile.getInputStream());
		}
		return thumbnail;
	}
}
