package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.constant.GlobalConstant;
import com.o2o.shop.dto.shopmanager.ManageShopDTO;
import com.o2o.shop.dto.superadmin.ShopDTO;
import com.o2o.shop.exception.BusinessException;
import com.o2o.shop.exception.ExceptionCodeEnum;
import com.o2o.shop.service.AreaService;
import com.o2o.shop.service.LocalAuthService;
import com.o2o.shop.service.ShopCategoryService;
import com.o2o.shop.service.ShopService;
import com.o2o.shop.validator.area.AddGroup;
import com.o2o.shop.vo.LocalAuthVO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.HashMap;
import java.util.Map;

/**
 * ShopListManageController 后台管理商铺列表(Manager)
 * @author 勿忘初心
 * @since 2023-07-18-19:03
 * 商铺列表信息获取,创建新商铺
 */
@RestController
@RequestMapping("/manager")
public class ShopListManageController {

    @Autowired
    private LocalAuthService localAuthService;

    @Autowired
    private ShopService shopService;

    @Autowired
    private ShopCategoryService shopCategoryService;

    @Autowired
    private AreaService areaService;

    /**
     * 获取商家创建的商铺列表
     * @return
     */
    @GetMapping("/shop_list")
    PageVO getManagerShopList(){
        // 验证用户登录信息
        LocalAuthVO localAuthVO = localAuthService.getUserAuthSession();
        if(localAuthVO != null && localAuthVO.getPersonInfo() != null){
            // 验证当前请求的权限
            if(!localAuthVO.getPersonInfo().getUserType().equals(GlobalConstant.RoleType.MANAGER.getValue())){
                throw new BusinessException(ExceptionCodeEnum.EC10004);
            }
        }else{
            // 未登录状态
            throw new BusinessException(ExceptionCodeEnum.EC20005);
        }
        // 条件查询
        ShopDTO shopDTO = ShopDTO.builder()
                .ownerId(localAuthVO.getPersonInfo().getUserId())
                .build();
        // 默认只有10个店铺
        return shopService.queryShopListPage(1,10,shopDTO);
    }

    /**
     * 创建商铺时先获取初始信息
     * 获取二级分类,以及当前的区域信息
     * @return
     */
    @GetMapping("/shop_init")
    ResultDataVO getInitShopInfo(){
        // 二级分类
        PageVO shopCategoryList = shopCategoryService.queryAllShopCategoryListPage(1,-1,new ShopCategoryBO(), false);
        // 区域信息
        PageVO areaList = areaService.queryAreaList();
        // Map封装
        Map<String,Object> initInfo = new HashMap<>(2);
        initInfo.put("shopCategoryList",shopCategoryList);
        initInfo.put("areaList",areaList);
        return ResultDataVO.success(initInfo);
    }

    /**
     * 商铺创建
     * @param managerShopDTO 商铺创建信息DTO对象
     * @param multipartFile 商铺缩略图对象
     * @return
     */
    @PostMapping("/shop_register")
    ResultDataVO createShop(@Validated(AddGroup.class) ManageShopDTO managerShopDTO,
                            @RequestParam MultipartFile multipartFile){
        if(multipartFile.isEmpty()){
            throw new BusinessException(ExceptionCodeEnum.EC10008);
        }
        return shopService.createShop(managerShopDTO,multipartFile);
    }

    /**
     * 删除商铺接口暂不对外开放
     * @return
     */
}
