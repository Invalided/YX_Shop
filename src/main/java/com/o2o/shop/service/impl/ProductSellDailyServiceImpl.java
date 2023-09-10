package com.o2o.shop.service.impl;

import com.o2o.shop.service.ProductSellDailyService;
import org.springframework.stereotype.Service;

/**
 * @author 勿忘初心
 * @since 2023-09-09-16:31
 * 每日销量统计
 */
@Service
public class ProductSellDailyServiceImpl implements ProductSellDailyService {
    /**
     * 商铺每日商铺销量
     */
    @Override
    public void dailyCalculate() {
        System.out.println("Quartz 已经启动啦!");
    }
}
