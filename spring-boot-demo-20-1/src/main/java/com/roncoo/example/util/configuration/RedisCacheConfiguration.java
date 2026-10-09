package com.roncoo.example.util.configuration;

import java.lang.reflect.Method;
import java.time.Duration;
import java.util.HashMap;
import java.util.Map;

import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurer;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;

/**
 * redis 自定义缓存管理器
 */
@Configuration
public class RedisCacheConfiguration implements CachingConfigurer {

	@Bean
	public CacheManager cacheManager(RedisConnectionFactory connectionFactory) {
		org.springframework.data.redis.cache.RedisCacheConfiguration defaultConfig = org.springframework.data.redis.cache.RedisCacheConfiguration
				.defaultCacheConfig()
				.entryTtl(Duration.ofSeconds(20));
		Map<String, org.springframework.data.redis.cache.RedisCacheConfiguration> cacheConfigurations = new HashMap<>();
		cacheConfigurations.put("roncooCache",
				org.springframework.data.redis.cache.RedisCacheConfiguration.defaultCacheConfig()
						.entryTtl(Duration.ofSeconds(200)));
		return RedisCacheManager.builder(connectionFactory)
				.cacheDefaults(defaultConfig)
				.withInitialCacheConfigurations(cacheConfigurations)
				.build();
	}

	@Override
	@Bean
	public KeyGenerator keyGenerator() {
		return (Object o, Method method, Object... objects) -> {
			StringBuilder sb = new StringBuilder();
			sb.append(o.getClass().getName());
			sb.append(method.getName());
			for (Object obj : objects) {
				sb.append(obj.toString());
			}
			return sb.toString();
		};
	}
}
