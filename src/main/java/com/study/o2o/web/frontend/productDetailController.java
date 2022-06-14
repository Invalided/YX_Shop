package com.study.o2o.web.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.Product;
import com.study.o2o.service.productService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/frontend")
public class productDetailController {
	@Autowired
	private productService productService;
	
	/**
	 * 根据商品Id获取商品详情
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/listproductdetailpageinfo",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductDetailPageInfo(HttpServletRequest request){
		Map<String , Object> modelMap = new HashMap<String, Object>();
		//获取前台传过来的productId
		long productId = httpServletRequestUtil.getLong(request, "productId");
		Product product = null;
		//空值判断
		if(productId != -1) {
			//根据productId获取商品信息，包含商品详情图
			product = productService.getProductById(productId);
			//2.0新增
			PersonInfo user = (PersonInfo)request.getSession().getAttribute("user");
			if(user == null) {
				modelMap.put("needQRCode", false);
			}else {
				modelMap.put("needQRCode",true);
			}
			modelMap.put("product", product);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty product");
		}
		return modelMap;
	}
}
