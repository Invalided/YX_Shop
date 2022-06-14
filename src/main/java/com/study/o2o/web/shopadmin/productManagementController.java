package com.study.o2o.web.shopadmin;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.productExecution;
import com.study.o2o.entity.Product;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.productCategory;
import com.study.o2o.enums.productStateEnum;
import com.study.o2o.exceptions.productOperationException;
import com.study.o2o.service.productCategoryService;
import com.study.o2o.service.productService;
import com.study.o2o.util.codeUtil;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
public class productManagementController {
	@Autowired
	private productService productService;
	@Autowired
	private productCategoryService productCategoryService;
	//支持最大上传商品详情图的最大数量
	private static final int IMAGEMAXCOUNT = 6;
	/**
	 * 商品添加
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/addproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProduct(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//验证码校验
		if(!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		//接收前端参数的变量的初始化,包括商品,缩略图,详情图列表实体类
		ObjectMapper mapper = new ObjectMapper();
		Product product = null;
		String productStr = httpServletRequestUtil.getString(request, "productStr");
 		imageHolder thumbnail = null;
		List<imageHolder> productImgList = new ArrayList<imageHolder>();
		CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		try {
			//若请求中存在文件流,则取出相关的文件(包括缩略图和详情图)
			if(multipartResolver.isMultipart(request)) {
				thumbnail = handleImage(request, productImgList);
			}else {
				modelMap.put("success",false);
				modelMap.put("errorMsg","上传图片不能为空");
				return modelMap;
			}
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			System.out.println("错误为:"+e.getStackTrace());
			return modelMap;
		}
		try {
			//尝试获取前端传过来的表单String流并将其转换为product实体类
			product = mapper.readValue(productStr, Product.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.toString());
			System.out.println("错误为:"+e.toString());
			return modelMap;
		}
		//若Product信息, 缩略图以及详情信息图列表为空, 则开始进行商品添加操作
		if(product!=null && thumbnail!=null && productImgList.size()>0) {
			try {
				//从session中获取当前店铺的Id并赋值给prodcut, 减少对前端数据的依赖
				Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
				product.setShop(currentShop);
				//执行添加操作
				productExecution pe = productService.addProduct(product, thumbnail, productImgList);
				if(pe.getState()==productStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else{
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());		
				}
			} catch (productOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入商品信息");
		}
		return modelMap;
	}
	private imageHolder handleImage(HttpServletRequest request, List<imageHolder> productImgList) throws IOException {
		imageHolder thumbnail;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest)request;
		//取出缩略图并构建imageHolder对象
		CommonsMultipartFile thumbnailFile = (CommonsMultipartFile) multipartRequest.getFile("thumbnail");
		
		thumbnail = new imageHolder(thumbnailFile.getOriginalFilename(), thumbnailFile.getInputStream());
		//取出详情图列表并构建List<imageHolder>列表对象,最多支持六张图上传
		for(int i = 0;i<IMAGEMAXCOUNT;i++) {
			CommonsMultipartFile productImgFile = (CommonsMultipartFile) multipartRequest.getFile("productImg"+i);
			//若取出的第i个详情图片文件流不为空, 则将其加入详情列表
			if(productImgFile!=null) {
				imageHolder productImg = new imageHolder(productImgFile.getOriginalFilename(), productImgFile.getInputStream());
				productImgList.add(productImg);
			}else {
				//若取出的第i个详情图片文件流为空, 则终止循环
				break;
			}
		}
		return thumbnail;
	}
	/**
	 * 商品查询
	 * @param productId
	 * @return
	 */
	@RequestMapping(value = "/getproductbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductById(@RequestParam Long productId){
		Map<String, Object> modelMap = new HashMap<>();
		//非空判断
		if(productId > -1) {
			//获取商品信息
			Product product = productService.getProductById(productId);
			//获取该店铺商铺下的商品类别列表
			List<productCategory> productCategoryList = productCategoryService
					.getProductCategoryList(product.getShop().getShopId());
			modelMap.put("product", product);
			modelMap.put("productCategoryList", productCategoryList);
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty productId");
		}
		return modelMap;
	}
	/**
	 * 商品编辑
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/modifyproduct",method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyProduct(HttpServletRequest request){
		Map<String,Object> modelMap = new HashMap<String, Object>();
		//是商品编辑时候调用还是上下架操作时候调用
		//若为前者则进行验证码判断，后者跳过验证码
		boolean statusChange = httpServletRequestUtil.getBoolean(request,"statusChange");
		//验证码判断
		if(!statusChange&&!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}
		// 接收前端参数的变量的初始化，包括商品，缩略图，详情图列表实体类
				ObjectMapper mapper = new ObjectMapper();
				Product product = null;
				imageHolder thumbnail = null;
				List<imageHolder> productImgList = new ArrayList<imageHolder>();
				CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(
						request.getSession().getServletContext());
				// 若请求中存在文件流，则取出相关的文件（包括缩略图和详情图）
				try {
					if (multipartResolver.isMultipart(request)) {
						System.out.println("value"+multipartResolver.isMultipart(request));
						thumbnail = handleImage(request, productImgList);
					}
				} catch (Exception e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
				try {
					String productStr = httpServletRequestUtil.getString(request, "productStr");
					// 尝试获取前端传过来的表单string流并将其转换成Product实体类
					product = mapper.readValue(productStr, Product.class);
				} catch (Exception e) {
					modelMap.put("success", false);
					modelMap.put("errMsg", e.toString());
					return modelMap;
				}
				// 非空判断
				if (product != null) {
					try {
						// 从session中获取当前店铺的Id并赋值给product，减少对前端数据的依赖
						Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
						product.setShop(currentShop);
						// 开始进行商品信息变更操作
						productExecution pe = productService.modifyProduct(product, thumbnail, productImgList);
						if (pe.getState() == productStateEnum.SUCCESS.getState()) {
							modelMap.put("success", true);
						} else {
							modelMap.put("success", false);
							modelMap.put("errMsg", pe.getStateInfo());
						}
					} catch (RuntimeException e) {
						modelMap.put("success", false);
						modelMap.put("errMsg", e.toString());
						return modelMap;
					}

				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", "请输入商品信息");
				}
				return modelMap;
		
	}
}
