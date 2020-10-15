package com.echo.backend.config;

import com.echo.backend.domain.Auction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;

@Configuration
public class BeanConfiguration {

    private Logger log = LoggerFactory.getLogger(BeanConfiguration.class);

    @Bean
    public ApplicationRunner runner(DataSource dataSource) {
        return args -> {
            log.info("dataSource: {}", dataSource);
            Connection connection = dataSource.getConnection();
            log.info("connection: {}", connection);
        };
    }

    @Bean(name = "schedulerTaskPool")
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskScheduler = new ThreadPoolTaskScheduler();
        taskScheduler.setPoolSize(10);
        return taskScheduler;
    }

    @Bean("taskExecutor")
    public Executor asyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        executor.setCorePoolSize(5);
        executor.setMaxPoolSize(20);
        executor.setQueueCapacity(500);
        executor.setKeepAliveSeconds(60);
        executor.setThreadNamePrefix("DailyAsync-");
        executor.initialize();
        return executor;
    }

    @Bean
    // Auction which will begin in 10mins
    Map<Integer, Auction> onAuctionMap(){
        return new ConcurrentHashMap<>();
    }

    @Bean
    // Ongoing auction
    Map<Integer, Auction> auctioningMap(){
        return new ConcurrentHashMap<>();
    }
}
