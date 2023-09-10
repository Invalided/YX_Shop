package com.o2o.shop.service;

/**
 * @author 勿忘初心
 * @since 2023-09-09-16:25
 * 每日销量统计
 * todo 具体的统计销量任务实现
 */
public interface ProductSellDailyService {
    /**
     * 商铺每日商铺销量
     */
    void dailyCalculate();

}
