package com.study.o2o.web.superadmin;

import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.AreaExecution;
import com.study.o2o.entity.Area;
import com.study.o2o.enums.AreaStateEnum;
import com.study.o2o.service.areaService;



@Controller
@RequestMapping("/superadmin")
@Api(description = "区域管理")
public class areaController {
	Logger logger = LoggerFactory.getLogger(areaController.class);
	@Autowired
	private areaService areaService;
	
	/**
	 * 获取区域信息
	 * @return
	 */
	@ApiOperation(value = "区域信息列表")
	//设置访问路由
	@RequestMapping(value = "/listarea",method = RequestMethod.GET)
	//将modelMap转换为JSON
	@ResponseBody
	private Map<String, Object> listArea(){
		logger.info("===start===");
		System.out.println("系统输出");
		long startTime = System.currentTimeMillis();
		//存放方法的返回值
		Map<String, Object> modelMap = new HashMap<String,Object>();
		//获取service层的区域列表
		List<Area> list = new ArrayList<Area>();
		try {
			list = areaService.getAreaList();
			modelMap.put("rows", list);
			modelMap.put("total", list.size());
		} catch (Exception e) {
			e.printStackTrace();
			modelMap.put("success",false);
			modelMap.put("errMsg", e.toString());
		}
		logger.error("test error");
		long endTime = System.currentTimeMillis();
		logger.debug("countTime:[{}ms]",endTime-startTime);
		logger.info("===end===");
		return modelMap;
	}
	/**
	 * 添加区域信息
	 * @param
	 * @return
	 */
	@RequestMapping(value = "/addarea",method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> addArea(String areaStr,HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Area area = null;
		try {
			//接收前端传递过来的area json 字符串信息并转换为Area实体类实列
			area = mapper.readValue(areaStr, Area.class);
			//decode可能有中文的地方
			area.setAreaName((area.getAreaName() == null)?null:URLDecoder.decode(area.getAreaName(),"UTF-8"));
			//添加创建时间和修改时间
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			return modelMap;
		}
		//空值判断
		if(area != null && area.getAreaName() != null) {
			try {
				//添加 区域信息
				AreaExecution ae = areaService.addArea(area);
				if(ae.getState() == AreaStateEnum.SUCCESS.getState()) {
					modelMap.put("success",true);
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
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}
	
	/**
	 * 修改区域信息
	 * 
	 * @param areaStr
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyarea",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyArea(String areaStr, HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		ObjectMapper mapper = new ObjectMapper();
		Area area = null;
		try {
			//接收前端传过来的area json 字符串并转为Area实体类实例
			System.out.println("areaStr "+areaStr);
			area = mapper.readValue(areaStr, Area.class);
			area.setAreaName((area.getAreaName() == null)?null:URLDecoder.decode(area.getAreaName(),"UTF-8"));
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg",e.toString());
			return modelMap;
		}
		// 空值判断
		if(area != null && area.getAreaId() != null) {
			try {
				//修改区域信息
				AreaExecution ae = areaService.modifyArea(area);
				if(ae.getState() == AreaStateEnum.SUCCESS.getState()) {
					modelMap.put("success",true);
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
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}
	
	/**
	 * 删除区域信息
	 * 
	 * @param areaId
	 * @return
	 */
	@RequestMapping(value = "/removearea",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> removeArea(Long areaId){
		Map<String, Object> modelMap = new HashMap<>();
		//空值判断
		if(areaId != null && areaId > 0) {
			try {
				//根据传入的Id删除对应的头条
				AreaExecution ae = areaService.removeArea(areaId);	
				if(ae.getState() == AreaStateEnum.SUCCESS.getState()) {
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
			modelMap.put("errMsg", "请输入区域信息");
		}
		return modelMap;
	}
}
