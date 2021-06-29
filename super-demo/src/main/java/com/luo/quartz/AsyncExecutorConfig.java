package com.luo.quartz;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.ThreadPoolExecutor;

/**
 * 类名称：AsyncExecutorConfig
 * 类描述：
 */
@Configuration
@EnableAsync
public class AsyncExecutorConfig {

    /**
     * 核心线程数
     */
    @Value("${spring.task.execution.pool.core-size:10}")
    private int corePoolSize;

    /**
     * 最大线程数
     */
    @Value("${spring.task.execution.pool.max-size:20}")
    private int maxPoolSize;

    /**
     * 线程活跃时间
     */
    @Value("${spring.task.execution.pool.keep-alive:10}")
    private int keepAliveSeconds;

    /**
     * 队列长度
     */
    @Value("${spring.task.execution.pool.queue-capacity:1000}")
    private int queueCapacity;

    @Bean("asyncExecutor")
    @Primary
    public ThreadPoolTaskExecutor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 配置核心线程数
        executor.setCorePoolSize(this.corePoolSize);
        // 配置最大线程数
        executor.setMaxPoolSize(this.maxPoolSize);
        // 配置线程活跃时间
        executor.setKeepAliveSeconds(keepAliveSeconds);
        // 配置队列大小
        executor.setQueueCapacity(this.queueCapacity);
        // 配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("executor thread: ");
        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        // 执行初始化
        executor.initialize();
        return executor;
    }
}
