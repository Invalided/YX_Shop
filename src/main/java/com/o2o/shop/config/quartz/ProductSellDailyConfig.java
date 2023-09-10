package com.o2o.shop.config.quartz;

import com.o2o.shop.service.ProductSellDailyService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.quartz.CronScheduleBuilder;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @author 勿忘初心
 * @since 2023-09-09-21:07
 * 每日销量定时统计,实现ing
 */
@Slf4j
@Configuration
public class ProductSellDailyConfig extends AbstractIntervalJobs{

    @Value("${quartz.sellDaily.name:''}")
    private String name;
    @Value("${quartz.sellDaily.group:''}")
    private String group;
    @Value("${quartz.sellDaily.crontabExpression:''}")
    private String crontabExpression;
    @Autowired
    private ProductSellDailyService productSellDailyService;


    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("ProductSellDailyConfig 执行");
        productSellDailyService.dailyCalculate();
    }


    /**
     * 初始任务参数
     */
    @PostConstruct
    @Override
    void initProperties() {
        super.name = name;
        super.group = group;
    }

    /**
     * 构建不同的任务定时器
     *
     * @return
     */
    @Override
    ScheduleBuilder scheduleBuilder() {
        if(StringUtils.isBlank(crontabExpression)){
            log.error("销量统计暂无法执行,请检查配置信息是否存在");
        }
        return CronScheduleBuilder
                // crontab表达式
                .cronSchedule(crontabExpression)
                .withMisfireHandlingInstructionFireAndProceed();
    }
}
