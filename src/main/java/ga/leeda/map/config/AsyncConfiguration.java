package ga.leeda.map.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;

/**
 * 비동기 처리를 위한 Executor의 기본 설정을 지정
 */
@EnableAsync
@Configuration
public class AsyncConfiguration {

    @Bean(name = "threadPoolTaskExecutor")
    public Executor threadPoolTastExecutor() {
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        taskExecutor.setCorePoolSize(3);
        taskExecutor.setMaxPoolSize(30);
        taskExecutor.setQueueCapacity(10);
        taskExecutor.setThreadNamePrefix("ASYNC-TASK-");
        taskExecutor.initialize();
        return taskExecutor;
    }
}
