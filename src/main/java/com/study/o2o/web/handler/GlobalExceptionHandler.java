package com.study.o2o.web.handler;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.exceptions.productOperationException;
import com.study.o2o.exceptions.shopOperationException;


//织入到所有被Spring所管理的Controller层中
@ControllerAdvice
public class GlobalExceptionHandler {
	private final static Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	@ExceptionHandler(value = Exception.class)
	@ResponseBody
	public Map<String, Object> Handle(Exception e){
		Map<String, Object> modelMap = new HashMap<String,Object>();
		modelMap.put("success", false);
		if(e instanceof shopOperationException) {
			modelMap.put("errMsg",e.getMessage());
		}else if(e instanceof productOperationException) {
			modelMap.put("errMsg",e.getMessage());
		}else {
			LOG.error("系统出现异常"+e.getMessage()+e.toString()+"trace");
			modelMap.put("errMsg", "未知错误,请联系工作人员解决");
		}
		return modelMap;
	}
}
