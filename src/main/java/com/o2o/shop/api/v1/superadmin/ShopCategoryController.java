package com.o2o.shop.api.v1.superadmin;

import com.o2o.shop.bo.ShopCategoryBO;
import com.o2o.shop.dto.superadmin.ShopCategoryDTO;
import com.o2o.shop.service.ShopCategoryService;
import com.o2o.shop.validator.area.UpdateGroup;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.groups.Default;

/**
 * ShopCategoryController 类别信息管理(Admin)
 * @author 勿忘初心
 * @since 2023-07-09-0:20
 */
@RestController
@RequestMapping("/admin")
@Slf4j
@Validated
public class ShopCategoryController {


    @Autowired
    private ShopCategoryService shopCategoryService;

    /**
     * 获取shopCategory所有信息列表
     * @param pageNum 当前页码
     * @param pageSize  页面数据量
     * 该请求可无需ShopCategoryDTO对象
     * @return
     */
    @GetMapping("/shop_category/all")
    PageVO queryAllShopCategory(@NotNull @Range(min = 1, max = 100, message = "参数错误") Integer pageNum,
                                @NotNull @Range(min = 1, max = 20, message = "参数错误") Integer pageSize) {
        return shopCategoryService.queryAllShopCategoryListPage(pageNum, pageSize, null,true);

    }

    /**
     * 不分页返回所有的一级列表
     * @return
     */
    @GetMapping("/shop_category/primary")
    PageVO queryPrimaryShopCategory(){
        // 返回全部一级列表，不分页
        return shopCategoryService.queryAllShopCategoryListPage(1, -1, null,false);
    }

    /**
     * 不分页返回所有的二级列表
     * @return
     */
    @GetMapping("/shop_category/secondary")
    PageVO querySecondaryShopCategory(){
        // 构建查询条件
        ShopCategoryBO shopCategoryBO = new ShopCategoryBO();
        // 返回全部，不分页
        return shopCategoryService.queryAllShopCategoryListPage(1, -1, shopCategoryBO,false);
    }


    /**
     * @param shopCategoryDTO 商铺类别信息DTO对象
     * @param categoryImg 类别图片,提交时为form-data格式,注意刷新
     * @return
     */
    @PostMapping("/shop_category/add")
    ResultDataVO addShopCategory(@Validated ShopCategoryDTO shopCategoryDTO,
                                 @NotNull(message = "图片不能为空") MultipartFile categoryImg){

        return shopCategoryService.createShopCategory(shopCategoryDTO, categoryImg);
    }

    /**
     * 更新类别信息
     * @param shopCategoryDTO 类别传输对象
     * @param categoryImg 类别图片，非必须
     * 需要使用Update参数以及默认参数时，需要显式声明Default参数，否则将不生效
     * @return
     */
    @PutMapping("/shop_category/update")
    ResultDataVO updateShopCategory(@Validated(value = {UpdateGroup.class, Default.class}) ShopCategoryDTO shopCategoryDTO,
                                    @RequestParam(required = false) MultipartFile categoryImg){
        return shopCategoryService.updateShopCategory(shopCategoryDTO, categoryImg);
    }

    /**
     * 根据id删除对应类别信息
     * @param id 类别信息id
     * @RequestParam 该注解将指定该方法必须携带某个参数才可执行，否则会出现异常，并覆盖后面的自定义信息
     * @return
     */
    @DeleteMapping("/shop_category/del")
    ResultDataVO deleteShopCategory(@RequestParam @NotNull(message = "不能为空") Integer id){
        return shopCategoryService.deleteShopCategoryById(id);
    }

}
