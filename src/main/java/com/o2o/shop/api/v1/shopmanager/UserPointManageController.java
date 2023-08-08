package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.shopmanager.ManagePointDTO;
import com.o2o.shop.service.UserShopMapService;
import com.o2o.shop.vo.PageVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * UserPointManageController 商家积分管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-23-23:29
 */
@RestController
@RequestMapping("/manager")
@Validated
public class UserPointManageController {

    @Autowired
    private UserShopMapService userShopMapService;

    @GetMapping("/point/list")
    PageVO queryShopPointInfo(@RequestBody ManagePointDTO managePointDTO,
                              @Range(min = 1,max = 100,message = "pageNum不合法") Integer pageNum,
                              @Range(min = 1,max = 10,message = "pageSize不合法") Integer pageSize){
        return userShopMapService.queryUserPointListByCondition(managePointDTO,pageNum,pageSize);
    }
}
