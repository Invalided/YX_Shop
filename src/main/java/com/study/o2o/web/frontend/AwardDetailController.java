package com.study.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.entity.Award;
import com.study.o2o.service.AwardService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping(value = "/frontend")
public class AwardDetailController {
	
	@Autowired
	private AwardService awardService;
	
	@RequestMapping(value = "/listawarddetailpageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> showAwardDetail(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<>();
		//获取awardId
		int awardId = httpServletRequestUtil.getInt(request, "awardId");
		Award award = null;
		if(awardId!=-1) {
			award = awardService.getAwardById(awardId);
			modelMap.put("award", award);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty award");
		}
		return modelMap;
	}
}
