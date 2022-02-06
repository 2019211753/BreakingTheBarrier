package com.lrm.config;

import com.lrm.util.VisibleThreadPoolTaskExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class TaskPoolConfig {
    private static Logger logger = LoggerFactory.getLogger(TaskPoolConfig.class);

    //使用别名alias，调用的时候需要async("file_task")
    @Bean("file_task")
    public Executor taskExecutor() {
        logger.info("start asyncFileTaskExecutor");
        ThreadPoolTaskExecutor executor = new VisibleThreadPoolTaskExecutor();
        //配置核心线程数
        executor.setCorePoolSize(5);
        //配置最大线程数
        executor.setMaxPoolSize(8);
        //配置队列大小
        executor.setQueueCapacity(100);
        //配置线程池中的线程的名称前缀
        executor.setThreadNamePrefix("async-fileTask-");

        // rejection-policy：当pool已经达到max size的时候，如何处理新任务
        // CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
        // DISCARD_OLDEST: 下载太久未响应，使用discardOldest逻辑
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardOldestPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }
}
