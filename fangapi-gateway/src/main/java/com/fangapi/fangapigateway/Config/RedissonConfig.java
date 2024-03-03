package com.fangapi.fangapigateway.Config;

import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties("spring.redis")
@Data
public class RedissonConfig {

    private Integer database;

    private String host;

    private Integer port;

    private String password;

    @Bean
    public RedissonClient redissonClient(){
        // redisson 配置
        Config config = new Config();
        // 使用单机配置
        config.useSingleServer()
                // 设置地址
                .setAddress("redis://" + host + ":" + port)
                // 设置密码
                .setPassword(password)
                // 设置数据库
                .setDatabase(database);
        RedissonClient redissonClient = Redisson.create(config);
        return redissonClient;
    }
}
