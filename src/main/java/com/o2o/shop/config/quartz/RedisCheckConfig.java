package com.o2o.shop.config.quartz;

import com.o2o.shop.util.RedisOperator;
import lombok.extern.slf4j.Slf4j;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.quartz.ScheduleBuilder;
import org.quartz.SimpleScheduleBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author 勿忘初心
 * @since 2023-09-09-19:09
 * Redis定时连接状态检测
 */
@Slf4j
@Component
public class RedisCheckConfig extends AbstractIntervalJobs {

    @Autowired
    private RedisOperator redisOperator;

    /**
     * 任务名称
     */
    @Value("${quartz.redis.name:}")
    private String name;

    /**
     * 分组名称
     */
    @Value("${quartz.redis.group:}")
    private String group;

    /**
     * 任务执行周期,默认为5分钟
     */
    @Value("${quartz.redis.intervalTime:300}")
    private Integer intervalTime;

    /**
     * redis连接状态
     */
    public static boolean redisConnected;

    
    /**
     * 初始任务参数,在Bean装配后执行
     */
    @PostConstruct
    @Override
    void initProperties() {
        super.group = group;
        super.name = name;
    }

    /**
     * 定时任务
     * @param context
     * @throws JobExecutionException
     */
    @Override
    protected void executeInternal(JobExecutionContext context) throws JobExecutionException {
        log.info("开始检测Redis连接状态,");
        redisConnected = redisOperator.ping();
        log.info("Redis当前是否连接 "+ redisConnected);
    }


    /**
     * 不同定时器
     *
     * @return
     */
    @Override
    public ScheduleBuilder scheduleBuilder() {
        return SimpleScheduleBuilder
                .simpleSchedule()
                .withIntervalInSeconds(intervalTime)
                // 永不过期
                .repeatForever();
    }
}
