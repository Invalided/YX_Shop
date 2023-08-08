package com.o2o.shop.service;

import com.o2o.shop.dto.mall.UserConsumptionDTO;
import com.o2o.shop.dto.mall.UserProductDTO;
import com.o2o.shop.vo.PageVO;
import com.o2o.shop.vo.ResultDataVO;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * 用户消费记录
 * @author 勿忘初心
 * @since 2023-07-06 12:17:37
 */
@Validated
public interface UserProductMapService {

    /**
     * 创建用户购物消费记录
     * @param consumptionList 消费商品对象集合
     * @return
     */
    ResultDataVO createUserConsumption(List<UserConsumptionDTO> consumptionList);


    /**
     * 根据条件查询订单信息
     * @param userProductDTO 条件查询
     * @param pageNum 当前页码
     * @param pageSize 页码数据量
     * @return
     */
    PageVO queryUserOrderInfoByCondition(@NotNull(message = "参数不能为空") UserProductDTO userProductDTO,
                                         Integer pageNum, Integer pageSize);
}
