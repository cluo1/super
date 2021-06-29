package com.luo.quartz;

import org.quartz.spi.TriggerFiredBundle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.scheduling.quartz.AdaptableJobFactory;
import org.springframework.stereotype.Component;

/**
 * 类名称：JobFactory
 * 类描述：任务工厂JobFactory（解决SpringBoot不能在Quartz中注入Bean的问题）
 * @version </pre>
 */
@Component
public class JobFactory extends AdaptableJobFactory {

    /**
     * AutowireCapableBeanFactory接口是BeanFactory的子类，可以连接和填充那些生命周期不被Spring管理的已存在的bean实例
     */
    @Autowired
    private AutowireCapableBeanFactory capableBeanFactory;

    /**
     * 创建Job实例
     */
    @Override
    protected Object createJobInstance(TriggerFiredBundle bundle) throws Exception {
        // 实例化对象
        Object jobInstance = super.createJobInstance(bundle);
        // 进行注入（Spring管理该Bean）
        capableBeanFactory.autowireBean(jobInstance);
        // 返回对象
        return jobInstance;
    }
}