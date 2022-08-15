package com.study.o2o.web.shopadmin;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

import javax.servlet.http.HttpServletRequest;

import com.study.o2o.service.ShopService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.study.o2o.dto.EchartSeries;
import com.study.o2o.dto.EchartXAxis;
import com.study.o2o.dto.UserProductMapExecution;
import com.study.o2o.entity.PersonInfo;
import com.study.o2o.entity.Product;
import com.study.o2o.entity.ProductSellDaily;
import com.study.o2o.entity.Shop;
import com.study.o2o.entity.UserProductMap;
import com.study.o2o.enums.UserProductMapStateEnum;
import com.study.o2o.service.ProductSellDailyService;
import com.study.o2o.service.UserProductMapService;
import com.study.o2o.service.productService;
import com.study.o2o.util.httpServletRequestUtil;

import net.sf.json.JSONArray;

@Controller
@RequestMapping("/shopadmin")
@Api(description = "用户商品管理")
public class UserProductManagementController {
	@Autowired
	private UserProductMapService userProductMapService;
	@Autowired
	private ProductSellDailyService productSellDailyService;
	@Autowired
	private productService productService;
	@Autowired
	private ShopService shopService;

	/**
	 * 列出每个店铺商品的销售情况
	 * @param request
	 * @return
	 */
	@ApiOperation(value =  "列出每个店铺商品的销售情况")
	@RequestMapping(value = "/listuserproductmapsbyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listUserProductMapsByShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取分页信息
		int pageIndex = httpServletRequestUtil.getInt(request, "pageIndex");
		int pageSize = httpServletRequestUtil.getInt(request, "pageSize");
		// 获取当前的店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 空值校验，确保shopId不为空
		if ((pageIndex > -1) && (pageSize > -1) && (currentShop != null) && (currentShop.getShopId() != null)) {
			// 添加查询条件
			UserProductMap userProductMapCondition = new UserProductMap();
			userProductMapCondition.setShop(currentShop);
			String productName = httpServletRequestUtil.getString(request, "productName");
			if (productName != null) {
				// 若前端想按照商品名模糊查询,则传入productName
				Product product = new Product();
				product.setProductName(productName);
				userProductMapCondition.setProduct(product);
			}
			// 根据传入的查询条件获取该店铺的商品销售情况
			UserProductMapExecution ue = userProductMapService.listUserProductMap(userProductMapCondition, pageIndex,
					pageSize);
			modelMap.put("userProductMapList", ue.getUserProductMapList());
			modelMap.put("count", ue.getCount());
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty pageSize or pageIndex or shopId");
		}
		return modelMap;
	}

	/**
	 * 列出店铺每日销情况
	 * @param request
	 * @return
	 */
	@ApiOperation(value = "列出每个店铺商品的销售情况")
	@RequestMapping(value = "/listproductselldailyinfobyshop", method = RequestMethod.GET)
	@ResponseBody
	private Map<String, Object> listProductSellDailyInfobyShop(HttpServletRequest request) {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取当前的店铺信息
		Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 空值校验,主要确保shopId不为空
		if ((currentShop != null) && (currentShop.getShopId() != null)) {
			// 添加查询条件
			ProductSellDaily productSellDailyCondition = new ProductSellDaily();
			productSellDailyCondition.setShop(currentShop);
			Calendar calendar = Calendar.getInstance();
			// 获取昨天的日期
			calendar.add(Calendar.DATE, -1);
			Date endTime = calendar.getTime();
			// 获取七天前的日期
			calendar.add(Calendar.DATE, -6);
			Date beginTime = calendar.getTime();
			// 根据传入的查询条件获取该店铺的商品销售情况
			List<ProductSellDaily> productSellDailyList = productSellDailyService
					.listProductSellDaily(productSellDailyCondition, beginTime, endTime);
			// 指定日期格式
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			// 商品名列表,保证唯一性
			HashSet<String> legendData = new HashSet<>();
			// x轴数据 尝试使用TreeSet 保证日期顺序
			// HashSet<String> xData = new HashSet<>();
			TreeSet<String> xData = new TreeSet<>();
			// 定义series
			List<EchartSeries> series = new ArrayList<EchartSeries>();
			// 日销量列表
			List<Integer> totalList = new ArrayList<>();
			// 当前商品名,默认为空
			String currentProductName = "";
			System.out.println("productSellDailyList item");
			for (ProductSellDaily ps : productSellDailyList) {
				System.out.println(ps.getCreateTime());
			}
			for (int i = 0; i < productSellDailyList.size(); i++) {
				ProductSellDaily productSellDaily = productSellDailyList.get(i);
				// 自动去重
				legendData.add(productSellDaily.getProduct().getProductName());
				xData.add(sdf.format(productSellDaily.getCreateTime()));
				if (!currentProductName.equals(productSellDaily.getProduct().getProductName())
						&& !currentProductName.isEmpty()) {
					// 如果currentProductName不等于获取的商品名,或者已遍历到列表的末尾,且currentProductName不为空
					// 则是遍历到下一个商品的日销量信息了,将前一轮遍历的信息放入series中
					// 包括了商品名以及商品对应的统计日及当日销量
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
					// 重置totalList
					totalList = new ArrayList<>();
					// 变换下currentProductId为当前productId
					currentProductName = productSellDaily.getProduct().getProductName();
					// 继续添加新的值
					totalList.add(productSellDaily.getTotal());
				} else {
					// 如果还是当前的productId则继续添加新值
					totalList.add(productSellDaily.getTotal());
					currentProductName = productSellDaily.getProduct().getProductName();
				}
				// 队列之末,需要将最后的一个商品销量信息也添加上
				if (i == productSellDailyList.size() - 1) {
					EchartSeries es = new EchartSeries();
					es.setName(currentProductName);
					es.setData(totalList.subList(0, totalList.size()));
					series.add(es);
				}
			}
			modelMap.put("series", series);
			modelMap.put("legendData", legendData);
			// 拼接出xAxis
			List<EchartXAxis> xAxis = new ArrayList<>();
			EchartXAxis exa = new EchartXAxis();
			exa.setData(xData);
			xAxis.add(exa);
			modelMap.put("xAxis", xAxis);
			modelMap.put("success", true);
		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty shopId");
		}
		return modelMap;
	}

	/**
	 * 05.22 写入一条用户消费记录 修改Method 为POST 修改返回值类型 为Map
	 * 
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@ApiOperation(value = "添加用户消费记录")
	@CrossOrigin
	@RequestMapping(value = "/adduserproductmap", method = RequestMethod.POST)
	@ResponseBody
	private @NotNull Map<String, Object> addUserProductMap(HttpServletRequest request) throws IOException {
		Map<String, Object> modelMap = new HashMap<>();
		// 获取添加消费记录所需要的参数并组建成userproductmap实例
		//Long productId = httpServletRequestUtil.getLong(request, "productid");
		Long customerId = httpServletRequestUtil.getLong(request, "uid");
		//Integer nums = httpServletRequestUtil.getInt(request, "nums");
		// 获取当前的店铺信息
		// Shop currentShop = (Shop) request.getSession().getAttribute("currentShop");
		// 获取当前商铺管理员Id
		// long shopOwnerId = currentShop.getOwner().getUserId();
		//获取前台消费列表
		String products = httpServletRequestUtil.getString(request, "products");
		JSONArray array = JSONArray.fromObject(products);
		List<UserProductMap> userProductMapList = new ArrayList<>();
		//赋值操作
		for (int i = 0; i < array.size(); i++) {
			Long pid = Long.valueOf(array.getJSONObject(i).getString("pid"));
			Integer count = Integer.valueOf(array.getJSONObject(i).getString("nums"));
			if(pid == null || count == null) {
				modelMap.put("success",false);
				modelMap.put("errMsg", "empty usre data");
				return modelMap;
			}
			userProductMapList.add(compactUserProductMap4Add(customerId, pid, count,1L));
		}
		// 空值校验 使用list存放
		if (customerId != -1 && userProductMapList!=null) {
			try {
				// 添加消费记录
				UserProductMapExecution se = userProductMapService.addUserProductMap(userProductMapList);
				if (se.getState() == UserProductMapStateEnum.SUCCESS.getState()) {
					modelMap.put("success", true);
				}
			} catch (RuntimeException e) {
				modelMap.put("success", false);
				modelMap.put("errMsg", e.getMessage());
			}

		} else {
			modelMap.put("success", false);
			modelMap.put("errMsg", "empty userProductMapList or costmerId");
		}
		return modelMap;
	}

	/**
	 * 05.22 删除操作员信息 新增nums 根据传入的customerId, productId组建用户消费记录
	 * 
	 * @param customerId
	 * @param productId
	 * @return
	 */
	private UserProductMap compactUserProductMap4Add(Long customerId, Long productId, Integer nums,Long operatorId) {
		UserProductMap userProductMap = null;
		if (customerId != null && productId != null && nums != null) {
			userProductMap = new UserProductMap();
			PersonInfo customer = new PersonInfo();
			customer.setUserId(customerId);
			PersonInfo operator = new PersonInfo();
			// 主要为了获取商品积分
			Product product = productService.getProductById(productId);
			// 从Product信息中获取ShopId
			Shop shop = shopService.getByShopId(product.getShop().getShopId());
			// 设置默认的操作员工的id值为当前店铺的拥有者
			operator.setUserId(shop.getOwnerId());
			userProductMap.setProduct(product);
			userProductMap.setShop(product.getShop());
			userProductMap.setUser(customer);
			userProductMap.setPoint(product.getPoint());
			userProductMap.setCreateTime(new Date());
			userProductMap.setOperator(operator);
			userProductMap.setNums(nums);
			// 设定默认值
			userProductMap.setCreateTime(new Date());
		}
		return userProductMap;
	}
}
