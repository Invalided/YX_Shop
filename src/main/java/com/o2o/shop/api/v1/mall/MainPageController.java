package com.o2o.shop.api.v1.mall;

import com.o2o.shop.bo.HeadLineBO;
import com.o2o.shop.service.HeadLineService;
import com.o2o.shop.service.ShopCategoryService;
import com.o2o.shop.vo.HeadLineVO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import com.o2o.shop.vo.ShopCategoryVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * MainPageController 商铺前台主页面
 * @author 勿忘初心
 * @since 2023-07-15-14:04
 * 首页信息加载
 */
@RestController
@RequestMapping("/mall")
public class MainPageController {
    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private HeadLineService headLineService;

    /**
     * 获取首页信息
     * @return
     */
    @GetMapping("/home")
    ResultDataVO listMainPage(){
        // 指定返回启用状态的头条信息
        HeadLineBO headLineCondition = new HeadLineBO(null,1);
        PageVO<HeadLineVO> headLineVOList = headLineService.allHeadLineList(1,4, headLineCondition);
        // 获取首页一级分类信息
        PageVO<ShopCategoryVO> shopCategoryVOList =  shopCategoryService.queryAllShopCategoryListPage(1,-1,
                null,false);
        // 以Map形式返回
        Map<String,Object> mainInfoMap = new HashMap<>(2);
        mainInfoMap.put("headLineList",headLineVOList);
        mainInfoMap.put("shopCategoryList",shopCategoryVOList);
        return ResultDataVO.success(mainInfoMap);
    }
}
