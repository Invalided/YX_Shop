package com.o2o.shop.config.quartz;

import lombok.extern.slf4j.Slf4j;
import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.Map;

/**
 * @author 勿忘初心
 * @since 2023-09-09-16:23
 * 定时任务,用于统计商铺每日销量
 */
@Configuration
@Slf4j
public class QuartzConfiguration {

    @Autowired
    private ApplicationContext applicationContext;

    @Resource
    private Scheduler scheduler;

    /**
     * Constructor(构造方法) -> @Autowired(依赖注入) -> @PostConstruct(注释的方法)
     * 获取所有实现了任务标记接口类,
     */
    @PostConstruct
    public void getInitBeans() {
        log.info("开始获取定时任务");
        Map<String, JobMarkedInterface> jobMarkedBeans = applicationContext.getBeansOfType(JobMarkedInterface.class);
        jobMarkedBeans.forEach((className, jobInstance) -> {
            if(jobInstance instanceof AbstractIntervalJobs){
                AbstractIntervalJobs intervalJobs = (AbstractIntervalJobs) jobInstance;
                log.info("任务名称: "+intervalJobs.name);
                log.info("任务分组: "+intervalJobs.group);

                // 定时任务不存在,无法执行
                if(intervalJobs.scheduleBuilder() == null){
                    log.error(className + " 任务无法运行, 请指定任务的运行周期时间后再试!");
                    return;
                }

                JobDetail jobDetail = jobDetail(intervalJobs);
                Trigger trigger = trigger(jobDetail,intervalJobs);

                try {
                    scheduler.scheduleJob(jobDetail, trigger);
                    // crontab 表达式的任务不会立即执行,如需立即执行则取消下一行代码的注释
                    // scheduler.triggerJob(jobDetail.getKey());
                    log.info("已添加 "+intervalJobs.name+" 任务 "+" jobKey"+jobDetail.getKey());
                } catch (SchedulerException e) {
                    log.error("定时任务出现异常");
                    e.printStackTrace();
                }
            }else{
                log.error(className + " 非AbstractIntervalJobs实例对象无法执行");
            }

        });
        log.info("获取定时任务结束");
    }

    /**
     * 任务详情
     * @param intervalJobs
     * @return
     */
    public JobDetail jobDetail(AbstractIntervalJobs intervalJobs) {
        return JobBuilder.newJob(intervalJobs.getClass())
                .withIdentity(intervalJobs.name,intervalJobs.group)
                .withDescription("内存运行")
                .storeDurably()
                .build();
    }

    /**
     * 触发器
     *
     * @return
     */
    public Trigger trigger(JobDetail jobDetail, AbstractIntervalJobs jobs) {
        return TriggerBuilder
                .newTrigger()
                .withIdentity(jobs.name, jobs.group)
                .forJob(jobDetail)
                .startNow()
                // crontab 表达式 0/5 * * * * ? 即每5s执行一次
                .withSchedule(jobs.scheduleBuilder())
                .build();
    }


}
