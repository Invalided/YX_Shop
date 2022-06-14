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

import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.ConstantForSuperAdmin;
import com.study.o2o.dto.HeadLineExecution;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.entity.headLine;
import com.study.o2o.enums.HeadLineStateEnum;
import com.study.o2o.service.headLineService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
public class HeadLineController {
	@Autowired
	private headLineService headLineService;

	/**
	 * 根据查询条件分页获取头条列表
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/listheadlines", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listHeadLines(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 创建存放头条数据的List
		List<headLine> list = new ArrayList<headLine>();
		try {
			// 若传入的条件中可用状态，则依据可用状态检索
			Integer enableStatus = httpServletRequestUtil.getInt(request, "enableStatus");
			headLine headLine = new headLine();
			if (enableStatus > -1) {
				headLine.setEnableStatus(enableStatus);
			}
			list = headLineService.getHeadLineList(headLine);
			modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, list);
			modelMap.put(ConstantForSuperAdmin.TOTAL, list.size());
		} catch (IOException e) {
			e.printStackTrace();
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		return modelMap;
	}

	/**
	 * 添加头条信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addheadline", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addHaedLine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		headLine headLine = null;
		// 接收并转化相应的参数，包括头条信息及图片信息
		String headLineStr = httpServletRequestUtil.getString(request, "headLineStr");
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			headLine = mapper.readValue(headLineStr, headLine.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, "headTitleManagementAdd_lineImg");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 空值判断
		if (headLine != null && thumbnail != null) {
			try {
				// decode可能存在中文的地方
				headLine.setLineName(
						(headLine.getLineName() == null) ? null : URLDecoder.decode(headLine.getLineName(), "UTF-8"));
				headLine.setLineLink(
						(headLine.getLineLink() == null) ? null : URLDecoder.decode(headLine.getLineLink(), "UTF-8"));
				// 添加头条信息
				HeadLineExecution headLineExecution = headLineService.addHeadLine(headLine, thumbnail);
				if (headLineExecution.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", headLineExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}

	/**
	 * 修改头条信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyheadline", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyHeadLine(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		headLine headLine = null;
		// 接收并转化相应的参数，包括头条信息及图片信息
		String headLineStr = httpServletRequestUtil.getString(request, "headLineStr");
		imageHolder thumbnail = null;
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			headLine = mapper.readValue(headLineStr, headLine.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}

		// 咱们的请求中都带有multi字样，因此没法过滤，只是用来拦截外部非图片流的处理
		try {
			if (multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, thumbnail, "headTitleManagementAdd_lineImg");
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		// 空值判断 03.12 去除对图片流的验证 交由service层验证 实现存在图片则修改
		if (headLine != null) {
			try {
				// decode可能存在中文的地方
				headLine.setLineName(
						(headLine.getLineName() == null) ? null : URLDecoder.decode(headLine.getLineName(), "UTF-8"));
				headLine.setLineLink(
						(headLine.getLineLink() == null) ? null : URLDecoder.decode(headLine.getLineLink(), "UTF-8"));
				// 修改头条信息
				HeadLineExecution headLineExecution = headLineService.modifyHeadLine(headLine, thumbnail);
				if (headLineExecution.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", headLineExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}
	
	/**
	 * 删除单个头条信息
	 * 
	 * @param headLineId
	 * @return
	 */
	@RequestMapping(value = "/removeheadline",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeHeadLine(Long headLineId){
		Map<String, Object> modelMap = new HashMap<>();
		//空值判断
		if(headLineId != null && headLineId > 0) {
			try {
				//根据传入的Id删除对应的头条
				HeadLineExecution headLineExecution = headLineService.removeHeadLine(headLineId);	
				if(headLineExecution.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", headLineExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
		}
		return modelMap;
	}

	/**
	 * 批量删除头条信息
	 * 
	 * @param headLineIdListStr
	 * @return
	 */
	@RequestMapping(value = "/removeheadlines",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeHeadLines(String headLineIdListStr){
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		JavaType javaType = mapper.getTypeFactory().constructParametricType(ArrayList.class,Long.class);
		List<Long> headLineIdList = null;
		try {
			// 将前端传入的Id列表字符串转换成List<Integer>
			headLineIdList = mapper.readValue(headLineIdListStr, javaType);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
		}
		// 空值判断
		if(headLineIdList != null && headLineIdList.size() > 0) {
			try {
				// 根据传入的头条Id批量删除头条信息
				HeadLineExecution headLineExecution = headLineService.removeHeadLineList(headLineIdList);
				if(headLineExecution.getState() == HeadLineStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", headLineExecution.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入头条信息");
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
		MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
		// 取出缩略图并构建imageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartHttpServletRequest.getFile(flowName);
		if (thumbnailFile != null) {
			thumbnail = new imageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		}
		return thumbnail;
	}

}
