package com.finpoints.bss.common.config;

import com.finpoints.bss.common.lock.impl.JdkLockProvider;
import com.finpoints.bss.common.lock.impl.RedissonLockProvider;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class LockProviderConfig {

    @Bean
    @ConditionalOnBean(RedissonClient.class)
    public RedissonLockProvider redissonLockProvider(Redisson redisson) {
        return new RedissonLockProvider(redisson);
    }

    @Bean
    @ConditionalOnMissingBean(JdkLockProvider.class)
    public JdkLockProvider jdkLockProvider() {
        return new JdkLockProvider();
    }
}
