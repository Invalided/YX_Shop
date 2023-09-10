package com.o2o.shop.config.quartz;

import org.quartz.ScheduleBuilder;
import org.springframework.scheduling.quartz.QuartzJobBean;

/**
 * @author 勿忘初心
 * @since 2023-09-09-19:02
 * 定时任务抽象类,用于指定子类需要实现属性及方法
 */
public abstract class AbstractIntervalJobs extends QuartzJobBean implements JobMarkedInterface {

    /**
     * 任务名称
     * @return
     */
    public String name;

    /**
     * 分组名称
     * @return
     */
    public String group;

    /**
     * 初始任务参数,实现类需要添加@PostConstruct注解
     */
    abstract void initProperties();

    /**
     * 构建不同的任务定时器
     * @return
     */
    abstract ScheduleBuilder scheduleBuilder();
}
