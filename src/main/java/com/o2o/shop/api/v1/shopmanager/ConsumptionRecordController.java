package com.o2o.shop.api.v1.shopmanager;

import com.o2o.shop.dto.mall.UserProductDTO;
import com.o2o.shop.service.UserProductMapService;
import com.o2o.shop.vo.PageVO;
import org.hibernate.validator.constraints.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ConsumptionRecordController 消费记录管理(Manager)
 * @author 勿忘初心
 * @since 2023-07-23-17:40
 */
@RestController
@RequestMapping("/manager")
public class ConsumptionRecordController {

    @Autowired
    private UserProductMapService userProductMapService;

    /**
     * 获取当前商铺下的消费信息
     * @param userProductDTO DTO
     * @param pageNum 当前页码
     * @param pageSize 页面数据量
     * @return
     */
    @GetMapping("/consumption/list")
    PageVO queryShopConsumptionRecord(@RequestBody UserProductDTO userProductDTO,
                                      @Range(min = 1,max = 50,message = "pageNum不合法") Integer pageNum,
                                      @Range(min = 1,max = 10,message = "pageSize不合法") Integer pageSize){
        return userProductMapService.queryUserOrderInfoByCondition(userProductDTO, pageNum,pageSize);
    }
}
