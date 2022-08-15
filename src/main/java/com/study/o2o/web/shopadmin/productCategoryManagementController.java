package com.study.o2o.web.shopadmin;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.dto.productCategoryExecution;
import com.study.o2o.dto.productExecution;
import com.study.o2o.dto.result;
import com.study.o2o.entity.Product;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.productCategory;
import com.study.o2o.enums.productCategoryStateEnum;
import com.study.o2o.exceptions.productCategoryOperationException;
import com.study.o2o.service.productCategoryService;
import com.study.o2o.service.productService;
import com.study.o2o.util.httpServletRequestUtil;

@Controller
@RequestMapping("/shopadmin")
@Api(description = "商品分类")
public class productCategoryManagementController {
	@Autowired
	private productCategoryService productCategoryService;
	@Autowired
	private productService productService;

	/**
	 * 获取商品类别列表
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "获取商品类别列表")
	@RequestMapping(value="/getproductcategorylist",method = RequestMethod.GET)
	@ResponseBody
	private result<List<productCategory>> getProductCategoryList(HttpServletRequest request){
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		List<productCategory> list = null;
		if(currentShop!=null&&currentShop.getShopId()>0) {
			list = productCategoryService.getProductCategoryList(currentShop.getShopId());
			return new result<List<productCategory>>(true,list);
		}else {
			productCategoryStateEnum ps = productCategoryStateEnum.INNER_ERROR;
			return new result<List<productCategory>>(false,ps.getState(),ps.getStateInfo());
		}
	}
	/**
	 * 添加商品类别
	 * @param productCategoryList
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "添加商品类别")
	@RequestMapping(value = "/addproductcategorys", method = RequestMethod.POST)
	@ResponseBody
	private Map<String, Object> addProductCategorys(@RequestBody List<productCategory> productCategoryList,
			HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<String, Object>();
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		for (productCategory pc : productCategoryList) {
			pc.setShopId(currentShop.getShopId());
		}
		if (productCategoryList != null && productCategoryList.size() > 0) {
			try {
				productCategoryExecution pe = productCategoryService.batchAddProductCategory(productCategoryList);
				if (pe.getState() == productCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				} else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			} catch (productCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少输入一个商品类别");
		}
		return modelMap;
	}

	/**
	 * 删除商品类别
	 * @param productCategoryId
	 * @param request
	 * @return
	 */
	@ApiOperation("删除商品类别")
	@RequestMapping(value = "/removeproductcategory", method = RequestMethod.POST)
	@ResponseBody
	private Map<String,Object> removeProductCategory(Long productCategoryId,HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<>();
		if(productCategoryId!=null && productCategoryId > 0) {
			try {
				Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
				productCategoryExecution pe = productCategoryService.deleteProductCategory(productCategoryId, currentShop.getShopId());
				if(pe.getState() == productCategoryStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}else {
					modelMap.put("success", false);
					modelMap.put("errMsg", pe.getStateInfo());
				}
			}catch (productCategoryOperationException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.toString());
				return modelMap;
			}
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "请至少选择一个商品类别");
		}
		return modelMap;
	}

	/**
	 * 通过商铺获取商品列表
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "通过商铺获取商品列表")
	@RequestMapping(value = "/getproductlistbyshop",method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> getProductListByShop(HttpServletRequest request){
		Map<String, Object> modelMap = new HashMap<String, Object>();
		//获取前台传过来的页码
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		//获取前台传过来的每页要求返回的商品数上限
		int pageSize = httpServletRequestUtil.getInt(request,"pageSize");
		//从当前session中获取店铺信息, 主要是获取shopId
		Shop currentShop = (Shop)request.getSession().getAttribute("currentShop");
		//空值判断
		if((pageIndex>-1)&&(pageSize>-1)&&(currentShop!=null)&&(currentShop.getShopId()!=null)) {
			//获取传入的需要检索的条件,包括是需要从某个商品类别以及模糊查找商品名去筛选某个店铺下的商品列表
			//筛选的条件可以进行排列组合
			long productCategoryId = httpServletRequestUtil.getLong(request, "productCategoryId");
			String productName = httpServletRequestUtil.getString(request, "productName");
			Product productCondition = compactProductCondition(currentShop.getShopId(), productCategoryId, productName);
			//传入查询条件以及分页信息进行查询 返回相应的商品列表以及总数
			productExecution pe = productService.getProductList(productCondition, pageIndex, pageSize);
			modelMap.put("productList", pe.getProductList());
			modelMap.put("count", pe.getProductList());
			modelMap.put("success", true);
		}else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}
	
	private Product compactProductCondition(long shopId,long productCategoryId,String productName) {
		Product productCondition = new Product();
		Shop shop = new Shop();
		shop.setShopId(shopId);
		productCondition.setShop(shop);
		//若有指定类别的要求则添加进去
		if (productCategoryId != -1L) {
			productCategory productCategory = new productCategory();
			productCategory.setProductCategoryId(productCategoryId);
			productCondition.setProductCategory(productCategory);
		}
		//若有商品名模糊查询条件的要求则添加进去
		if(productName != null) {
			productCondition.setProductName(productName);
		}
		return productCondition;
	}
}
