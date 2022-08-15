package com.study.o2o.web.superadmin;

import java.util.ArrayList;
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

import com.study.o2o.dto.ConstantForSuperAdmin;
import com.study.o2o.dto.PersonInfoExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.enums.PersonInfoStateEnum;
import com.study.o2o.service.PersonInfoService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/superadmin")
@Api(description = "用户信息管理")
public class PersonInfoController {
	
	@Autowired
	private PersonInfoService personInfoService;
	/**
	 * 列出所有用户信息
	 * @param request
	 * @return
	 */
	@ApiOperation("列出用户信息")
	@RequestMapping(value = "/listpersonInfos",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> listPersonInfos(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		PersonInfoExecution pie = null;
		//获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, ConstantForSuperAdmin.PAGE_NO);
		int pageSize = httpServletRequestUtil.getInt(request, ConstantForSuperAdmin.PAGE_SIZE);
		if(pageIndex > 0 && pageSize > 0) {
			try {
				PersonInfo personInfo = new PersonInfo();
				int enableStatus = httpServletRequestUtil.getInt(request, "enableStatus");
				if(enableStatus > -1) {
					//若查询条件中有按照可用状态来查询，则将其作为查询条件传入
					personInfo.setEnableStatus(enableStatus);
				}
				String name = httpServletRequestUtil.getString(request, "name");
				if(name!=null) {
					//若查询条件中有按照名字来查询，则将其作为查询条件传入
					personInfo.setEnableStatus(enableStatus);
				}
				pie = personInfoService.getPersonInfoList(personInfo,pageIndex, pageSize);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
			if(pie.getPersonInfoList() != null) {
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, pie.getPersonInfoList());
				modelMap.put(ConstantForSuperAdmin.TOTAL, pie.getCount());
				modelMap.put("success",true);
			}else {
				modelMap.put(ConstantForSuperAdmin.PAGE_SIZE, new ArrayList<PersonInfo>());
				modelMap.put(ConstantForSuperAdmin.TOTAL, 0);
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
	 * 修改用户信息,主要是修改用户账号可用状态
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "修改用户信息")
	@RequestMapping(value = "/modifypersonInfo",method = RequestMethod.POST)
	@ResponseBody
	private Map<String , Object> modifyPersonInfo(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		//从前端获取用户Id以及可用状态
		long userId = httpServletRequestUtil.getLong(request, "userId");
		int enableStatus = httpServletRequestUtil.getInt(request, "enableStatus");
		//非空判断
		if(userId >= 0 && enableStatus >= 0) {
			try {
				PersonInfo pi = new PersonInfo();
				pi.setUserId(userId);
				pi.setEnableStatus(enableStatus);
				//修改用户可用状态
				PersonInfoExecution pe = personInfoService.modifyPersonInfo(pi);
				if(pe.getState() == PersonInfoStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("success", pe.getStateInfo());
				}
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success",false);
			modelMap.put("errMsg", "请输入需要修改的账号信息");
		}
		return modelMap;
	}
}
