package com.zoura.home.work.threepagepath.conf;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.StringRedisTemplate;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class Config {

	@Bean
	public JedisPoolConfig jedisPoolConfig(RedisProperties redisProperties) {
		JedisPoolConfig poolConfig = new JedisPoolConfig();
		poolConfig.setMaxIdle(redisProperties.getRedisPoolMaxIdle());
		poolConfig.setMinIdle(redisProperties.getRedisPoolMinIdle());
		poolConfig.setMaxWaitMillis(redisProperties.getRedisPoolMaxWait());
		return poolConfig;
	}

	@Bean
	JedisConnectionFactory jedisConnectionFactory(RedisProperties redisProperties, JedisPoolConfig config) {
		JedisConnectionFactory factory = new JedisConnectionFactory();
		factory.setHostName(redisProperties.getRedisHost());
		factory.setPassword(redisProperties.getPassword());
		factory.setPort(redisProperties.getRedisPort());
		factory.setPoolConfig(config);
		return factory;
	}

	@Bean
	StringRedisTemplate redis(JedisConnectionFactory factory) {
		final StringRedisTemplate template = new StringRedisTemplate();
		template.setConnectionFactory(factory);
		return template;
	}
}

