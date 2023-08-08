package com.o2o.shop.api.v1.mall;

import com.o2o.shop.dto.mall.UserConsumptionDTO;
import com.o2o.shop.dto.mall.UserProductDTO;
import com.o2o.shop.service.UserProductMapService;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.util.List;

/**
 * UserConsumptionController 用户消费记录
 * @author 勿忘初心
 * @since 2023-07-15-22:54
 */
@RestController
@RequestMapping("/mall")
@Validated
public class UserConsumptionController {

    @Autowired
    private UserProductMapService userProductMapService;

    /**
     * 用户购物订单创建
     * @param consumptionDTOList 消费信息列表(List类型,至少需要一条数据)
     * @return
     */
    @PostMapping("/pay")
    ResultDataVO createUserConsumption(@RequestBody
                                       @NotEmpty(message = "参数列表不能为空")
                                       @Valid List<UserConsumptionDTO> consumptionDTOList){
        return userProductMapService.createUserConsumption(consumptionDTOList);
    }

    /**
     * 用户订单列表接口
     * @param userId 用户id
     * @param shopId 商铺id
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/order")
    PageVO getUserOrderInfo(@NotNull(message = "用户id不能为空")
                            @Positive(message = "用户id不合法") Integer userId,
                            @NotNull(message = "商铺id不能为空")
                            @Positive(message = "商铺id不合法") Integer shopId,
                            @Range(min = 1,max = 100,message = "参数错误") Integer pageNum,
                            @Range(min = 1,max = 10,message = "参数错误") Integer pageSize){
        // 设置分页参数
        if(pageSize == null){
            pageSize = 10;
        }
        UserProductDTO userProductDTO = new UserProductDTO();
        userProductDTO.setUserId(userId);
        userProductDTO.setShopId(shopId);
        return userProductMapService.queryUserOrderInfoByCondition(userProductDTO, pageNum, pageSize);
    }
}
