package com.yupi.project.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.validation.constraints.NotNull;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Configuration
public class ThreadPoolExecutorConfig {
    @Bean
    public ThreadPoolExecutor threadPoolExecutor() {
        // 创建线程工厂
        ThreadFactory threadFactory = new ThreadFactory(){
        private int count = 1;
            @Override
            public Thread newThread(@NotNull Runnable r) {
                Thread thread = new Thread(r);
                // 给线程起一个名字，名称中包含线程数的当前值
                thread.setName("线程" + count);
                count++;
                return thread;
            }
        };
        // 创建一个线程池，线程池的核心大小为 2 ，最大线程数为 4，线程的空闲时间为 100s 任务队列为阻塞队列，长度为 4 。
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(2,4,100, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(4),threadFactory);
        // 返回创建的线程池
        return threadPoolExecutor;
    }
}