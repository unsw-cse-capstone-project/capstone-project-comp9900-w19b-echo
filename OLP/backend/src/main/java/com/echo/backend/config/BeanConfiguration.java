package com.echo.backend.config;

import com.echo.backend.dao.PropertyMapper;
import com.echo.backend.domain.Auction;
import com.echo.backend.domain.Property;
import org.apache.lucene.analysis.core.StopAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

import javax.sql.DataSource;
import java.io.IOException;
import java.sql.Connection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class BeanConfiguration {

    private Logger log = LoggerFactory.getLogger(BeanConfiguration.class);

    private final PropertyMapper propertyMapper;

    public BeanConfiguration(PropertyMapper propertyMapper) {
        this.propertyMapper = propertyMapper;
    }

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

    @Bean
    Map<String, String> mailTemplate() {
        HashMap<String, String> mailTemplate = new HashMap<>();
        mailTemplate.put("ResetPassword", "You are resetting your password, please make sure it is your own operation. " +
                "Your verify code is ${code}");
        mailTemplate.put("SuccessSeller", "Congrats! Your property \"${address}\" is sold with price ${price}, buyer is " +
                "${email}. View more details in www.onlineproperty.com.");
        mailTemplate.put("SuccessBuyer", "Congrats! You are the winner of property \"${address}\" with price $${price}, owner is " +
                "${email}. View more details in www.onlineproperty.com.");
        return mailTemplate;
    }

    @Bean
    Map<Integer, Integer> verifyCode() {
        return new HashMap<>();
    }

    @Bean("taskExecutor")
    public Executor doSomethingExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        // 核心线程数：线程池创建时候初始化的线程数
        executor.setCorePoolSize(10);
        // 最大线程数：线程池最大的线程数，只有在缓冲队列满了之后才会申请超过核心线程数的线程
        executor.setMaxPoolSize(20);
        // 缓冲队列：用来缓冲执行任务的队列
        executor.setQueueCapacity(500);
        // 允许线程的空闲时间60秒：当超过了核心线程之外的线程在空闲时间到达之后会被销毁
        executor.setKeepAliveSeconds(60);
        // 线程池名的前缀：设置好了之后可以方便我们定位处理任务所在的线程池
        executor.setThreadNamePrefix("do-something-");
        // 缓冲队列满了之后的拒绝策略：由调用线程处理（一般是主线程）
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.DiscardPolicy());
        executor.initialize();
        return executor;
    }

    @Bean
    public Directory ramDirectory() {

        try {

            List<Property> properties = propertyMapper.getAllLegalProperty();

            Directory dic = new RAMDirectory();
            IndexWriterConfig config = new IndexWriterConfig(Version.LUCENE_4_10_2, new StopAnalyzer());

            IndexWriter writer = new IndexWriter(dic, config);
            writer.deleteAll();
            properties.forEach(t->{
                Document doc = new Document();
                doc.add(new Field("pid", String.valueOf(t.getPid()), TextField.TYPE_STORED));
                doc.add(new Field("address", t.getAddress(), TextField.TYPE_STORED));
                try {
                    writer.addDocument(doc);
                } catch (IOException ignored) {}
            });

            writer.commit();
            writer.close();
            return dic;

        }
        catch (IOException ignored){}

        return null;
    }
}
