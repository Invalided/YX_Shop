package com.o2o.shop.api.v1.mall;

import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.dto.superadmin.ShopDTO;
import com.o2o.shop.service.AreaService;
import com.o2o.shop.service.ShopCategoryService;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Positive;
import java.util.HashMap;
import java.util.Map;

/**
 * ShopListController 商铺列表页面
 * @author 勿忘初心
 * @since 2023-07-15-15:59
 * 商铺列表页面，列出所有一级分类/二级分类、区域列表、商铺信息
 */
@RestController
@RequestMapping("/mall")
@Validated
public class ShopListController {

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    @Autowired
    private ShopService shopService;
    /**
     * 获取列表页的相关信息
     * @param parentId 一级类别Id
     * @return
     */
    @GetMapping("/list")
    ResultDataVO queryShopListInfo(@Positive(message = "参数不合法") Integer parentId){
        ShopCategoryBO shopCategoryCondition = null;
        ShopCategoryBO parent = null;
        // 判断需要加载一级/二级列表
        if(parentId != null){
            // 构建条件,指定parent对象,加载指定parentId下的二级列表
            parent = ShopCategoryBO.builder()
                    .shopCategoryId(parentId)
                    .build();
            shopCategoryCondition = ShopCategoryBO.builder()
                    .parent(parent).build();
        }

        PageVO shopCategoryList = shopCategoryService.queryAllShopCategoryListPage(1, -1,
                shopCategoryCondition, false);
        // 获取区域信息
        PageVO areaList = areaService.queryAreaList();
        // 使用Map封装数据
        Map<String,Object> listInfo = new HashMap<>(2);
        listInfo.put("shopCategoryList",shopCategoryList);
        listInfo.put("areaList",areaList);
        return ResultDataVO.success(listInfo);
    }

    /**
     * 获取指定条件下的商铺
     * @param shopDTO 商铺DTO对象
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/listCondition")
    PageVO listShopByCondition(ShopDTO shopDTO,
                               @Range(min = 1,max = 20,message = "参数错误") Integer pageNum,
                               @Range(min = 1,max = 10,message = "参数错误") Integer pageSize){
        // 指定默认加载数据量
        if(pageSize == null){
            pageSize = 5;
        }
        // 默认指定加载启用的店铺
        shopDTO.setEnableStatus(1);
        return shopService.queryShopListPage(pageNum, pageSize, shopDTO);
    }
}
