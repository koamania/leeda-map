package ga.leeda.map.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import redis.embedded.RedisServer;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

/**
 * Embedded redis 설정을 위한 클래스
 * bean initialize 시점에 서버를 start하고 destroy 시점에 shutdown
 */
@Component
@Profile("local")
public class EmbeddedRedisConfiguration {

    @Value("${spring.redis.port}")
    private int redisPort;

    private RedisServer redisServer;

    @PostConstruct
    public void startRedis() {
        redisServer = new RedisServer(redisPort);
        redisServer.start();

        Runtime.getRuntime().addShutdownHook(new Thread(this::stopRedis));
    }

    @PreDestroy
    public void stopRedis() {
        if (this.redisServer != null && this.redisServer.isActive()) {
            redisServer.stop();
        }
    }
}
