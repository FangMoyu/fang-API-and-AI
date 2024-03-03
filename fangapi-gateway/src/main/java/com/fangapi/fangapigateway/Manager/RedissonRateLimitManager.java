package com.fangapi.fangapigateway.Manager;

import org.redisson.api.RRateLimiter;
import org.redisson.api.RateIntervalUnit;
import org.redisson.api.RateType;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class RedissonRateLimitManager {

    @Resource
    private RedissonClient redissonClient;

    public boolean doRateLimit(String key){
        // 创建一个名称为 key 的限流器，初始化时设置最大访问速率是 50 次/秒
        RRateLimiter rateLimiter = redissonClient.getRateLimiter(key);
        rateLimiter.trySetRate(RateType.OVERALL, 50, 1, RateIntervalUnit.SECONDS);
        // 每当一个操作来了，就请求一个令牌
        // 如果拿不到令牌，就抛出异常
        return rateLimiter.tryAcquire(1);
    }
}
