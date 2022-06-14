package com.study.o2o.web.shopadmin;

import java.io.File;
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
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.study.o2o.dto.imageHolder;
import com.study.o2o.dto.shopExecution;
import com.study.o2o.entity.Area;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.shopCategory;
import com.study.o2o.enums.shopStateEnum;
import com.study.o2o.exceptions.shopOperationException;
import com.study.o2o.service.areaService;
import com.study.o2o.service.shopCategoryService;
import com.study.o2o.service.ShopService;
import com.study.o2o.util.codeUtil;
import com.study.o2o.util.httpServletRequestUtil;
import com.study.o2o.util.imageUtil;
import com.study.o2o.util.pathUtil;

@Controller
@RequestMapping("/shopadmin")
// 实现店铺管理逻辑
public class shopManagementController {
	@Autowired
	private ShopService shopService;
	@Autowired
	private shopCategoryService shopCategoryService;
	@Autowired
	private areaService areaService;
	/**
	 * 判断用户是否有shopId来进入管理页面
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping(value = "/getshopmanagementinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopManagementInfo(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		long shopId = httpServletRequestUtil.getLong(request, "shopId");
		// 判断用户是否具有shopId
		if (shopId <= 0) {
			// 从session读取shopId
			Object currentShopObj = request.getSession().getAttribute("currentShop");
			if (currentShopObj == null) {
				modelMap.put("redirect", true);
				modelMap.put("url", "/o2o/shopadmin/shoplist");
			} else {
				Shop currentShop = (Shop) currentShopObj;
				modelMap.put("redirect", false);
				modelMap.put("shopId", currentShop.getShopId());
			}
		} else {
			Shop currentShop = new Shop();
			currentShop.setShopId(shopId);
			request.getSession().setAttribute("currentShop", currentShop);
			modelMap.put("redirect", false);
		}
		return modelMap;
	}

	/**
	 * 显示店铺列表
	 * 
	 * @param request
	 * @return
	 */

	@RequestMapping(value = "/getshoplist", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopList(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 创建用户对象
		PersonInfo user = new PersonInfo();
		// user.setUserId(1L);
		// user.setName("test");
		// request.getSession().setAttribute("user",user);
		// 获取session中存在的用户信息
		user = (PersonInfo) request.getSession().getAttribute("shop");
		//若此时登录了用户 获取之前保存的商家session 以商家的session为载体
//		if(user.getUserType() != 2) {
//			user = (PersonInfo) request.getSession().getAttribute("shop");
//			//重新保存商家信息
//			request.getSession().setAttribute("user", user);
//		}
		if (user != null) {
			System.out.println("userName " + user.getName());
			try {
				Shop shopCondition = new Shop();
				shopCondition.setOwner(user);
				// 设置用户可创建的店铺数量
				shopExecution se = shopService.getShopList(shopCondition, 0, 100);
				modelMap.put("shopList", se.getShopList());
				// 列出店铺成功之后，将店铺放入session中作为权限验证依据，即该帐号只能操作它自己的店铺
				request.getSession().setAttribute("shopList", se.getShopList());
				modelMap.put("user", user);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}
		} else {
			modelMap.put("redirect", true);
			modelMap.put("url", "/o2o/local/login?usertype=2");
		}
		return modelMap;
	}

	/**
	 * 获取页面初始信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "/getshopinitinfo", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopInitInfo() {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		List<shopCategory> shopCategoryList = new ArrayList<shopCategory>();
		List<Area> areaList = new ArrayList<Area>();
		try {
			shopCategoryList = shopCategoryService.getShopCategoryList(new shopCategory());
			areaList = areaService.getAreaList();
			modelMap.put("shopCategoryList", shopCategoryList);
			modelMap.put("areaList", areaList);
			modelMap.put("success", true);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
		}

		return modelMap;
	}

	@RequestMapping(value = "/registershop", method = RequestMethod.POST)
	@ResponseBody
	// 注册店铺
	private Map<String, Object> registerShop(HttpServletRequest request) throws IOException {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码是否正确
		if (!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		// 1.接收并转换相应的参数，包括店铺信息以及图片信息
		String shopStr = httpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "上传图片不能为空");
			return modelMap;
		}
		// 2.注册店铺
		if (shop != null && shopImg != null) {
			// 用户注册后使用用户的session来注册
			PersonInfo owner = (PersonInfo) request.getSession().getAttribute("user");
			shop.setOwner(owner);
			File shopImgFile = new File(pathUtil.getImgBasePath() + imageUtil.getRandomFileName());
			try {
				shopImgFile.createNewFile();
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			}

			shopExecution se;
			imageHolder imageHolder = new imageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
			se = shopService.addShop(shop, imageHolder);
			if (se.getState() == shopStateEnum.CHECK.getState()) {
				modelMap.put("success", true);
				// 该用户可以操作的店铺列表 即一个用户可以拥有多个店铺
				@SuppressWarnings("unchecked")
				List<Shop> shopList = (List<Shop>) request.getSession().getAttribute("shopList");
				if (shopList == null || shopList.size() == 0) {
					shopList = new ArrayList<Shop>();
				}
				shopList.add(se.getShop());
				request.getSession().setAttribute("shopList", shopList);
			} else {
				modelMap.put("success", false);
				modelMap.put("errMsg", se.getStateInfo());
			}
			
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺信息");
		}
		return modelMap;
	}

	/**
	 * 修改店铺信息
	 * 
	 * @param request
	 * @return
	 */
	// 1.通过Id获取店铺信息
	@RequestMapping(value = "/getshopbyid", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getShopById(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Long shopId = httpServletRequestUtil.getLong(request, "shopId");
		if (shopId > -1) {
			try {
				Shop shop = shopService.getByShopId(shopId);
				List<Area> areaList = areaService.getAreaList();
				modelMap.put("shop", shop);
				modelMap.put("areaList", areaList);
				modelMap.put("success", true);
			} catch (Exception e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
			}
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;

	}

	// 2.修改店铺信息
	@RequestMapping(value = "/modifyshop", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> modifyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		// 验证码是否正确
		if (!codeUtil.checkVerifyCode(request)) {
			modelMap.put("success", false);
			modelMap.put("errMsg", "输入了错误的验证码");
			return modelMap;
		}

		// 1.接收并转换相应的参数，包括店铺信息以及图片信息
		String shopStr = httpServletRequestUtil.getString(request, "shopStr");
		ObjectMapper mapper = new ObjectMapper();
		Shop shop = null;
		try {
			shop = mapper.readValue(shopStr, Shop.class);
		} catch (Exception e) {
			modelMap.put("success", false);
			modelMap.put("errMsg", e.getMessage());
			return modelMap;
		}
		CommonsMultipartFile shopImg = null;
		CommonsMultipartResolver commonsMultipartResolver = new CommonsMultipartResolver(
				request.getSession().getServletContext());
		if (commonsMultipartResolver.isMultipart(request)) {
			MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
			shopImg = (CommonsMultipartFile) multipartHttpServletRequest.getFile("shopImg");
		}
		// 2.修改店铺信息
		if (shop != null && shop.getShopId() != null) {
			shopExecution se;
			try {
				if (shopImg == null) {
					se = shopService.modifyShop(shop, null);
				} else {
					imageHolder imageHolder = new imageHolder(shopImg.getOriginalFilename(), shopImg.getInputStream());
					se = shopService.modifyShop(shop, imageHolder);
				}

				if (se.getState() == shopStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", se.getStateInfo());
				}
			} catch (IOException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
				return modelMap;
			} catch (shopOperationException e) {
				// 添加对于商铺修改时图片为空的异常处理。 06.03
				modelMap.put("success", false);
				modelMap.put("errMsg", "缩略图不能为空");
				return modelMap;
			}
			return modelMap;
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请输入店铺Id");
			return modelMap;
		}
	}

	// private static void inputStreamToFile(InputStream ins,File file) {
	// FileOutputStream os = null;
	// try {
	// os = new FileOutputStream(file);
	// int bytesRead = 0;
	// byte[] buffer = new byte[1024];
	// while((bytesRead = ins.read(buffer))!=-1) {
	// os.write(buffer,0,bytesRead);
	// }
	// } catch (Exception e) {
	// throw new RuntimeException("调用inputStreamToFile产生异常:"+e.getMessage());
	// }finally{
	// try {
	// if(os!=null) {
	// os.close();
	// }
	// if(ins != null) {
	// ins.close();
	// }
	// } catch (Exception e) {
	// throw new RuntimeException("inputStreamToFile关闭io产生异常:"+e.getMessage());
	// }
	// }
	// }
}
