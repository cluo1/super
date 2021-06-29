package com.luo.quartz;

import org.quartz.Scheduler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.quartz.SchedulerFactoryBean;

/**
 * 类名称：QuartzConfig
 * 类描述：任务调度器 Scheduler
 * 修改备注：
 * @version </pre>
 */
@Configuration
public class QuartzConfig {

    private final JobFactory jobFactory;

    public QuartzConfig(JobFactory jobFactory){
        this.jobFactory = jobFactory;
    }

    /**
     * 配置SchedulerFactoryBean
     * 将一个方法产生为Bean并交给Spring容器管理
     */
    @Bean
    public SchedulerFactoryBean schedulerFactoryBean(ThreadPoolTaskExecutor asyncExecutor) {
        // Spring提供SchedulerFactoryBean为Scheduler提供配置信息，并被Spring容器管理其生命周期
        SchedulerFactoryBean factory = new SchedulerFactoryBean();
        // 设置自定义JobFactory，用于Spring管理Job bean
        factory.setJobFactory(jobFactory);
        factory.setTaskExecutor(asyncExecutor);
        factory.setOverwriteExistingJobs(true);
        return factory;
    }

    @Bean
    public Scheduler scheduler(ThreadPoolTaskExecutor asyncExecutor) {
        return schedulerFactoryBean(asyncExecutor).getScheduler();
    }
}