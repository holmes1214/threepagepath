package com.zoura.home.work.threepagepath.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
/**
 * redis的属性封装类
 * @author haozou
 *
 */
@Component
@ConfigurationProperties
public class RedisProperties {
	@Value("${spring.redis.pool.max-idle}")
    private int redisPoolMaxIdle;
    @Value("${spring.redis.pool.min-idle}")
    private int redisPoolMinIdle;
    @Value("${spring.redis.pool.max-active}")
    private int redisPoolMaxActive;
    @Value("${spring.redis.pool.max-wait}")
    private int redisPoolMaxWait;
    
    @Value("${spring.redis.host}")
    private String redisHost;
    @Value("${spring.redis.password}")
    private String password;
    @Value("${spring.redis.port}")
    private int redisPort;
    @Value("${spring.redis.timeout}")
    private  int redisTimeout;
    
	public int getRedisPoolMaxIdle() {
		return redisPoolMaxIdle;
	}
	public void setRedisPoolMaxIdle(int redisPoolMaxIdle) {
		this.redisPoolMaxIdle = redisPoolMaxIdle;
	}
	public int getRedisPoolMinIdle() {
		return redisPoolMinIdle;
	}
	public void setRedisPoolMinIdle(int redisPoolMinIdle) {
		this.redisPoolMinIdle = redisPoolMinIdle;
	}
	public int getRedisPoolMaxActive() {
		return redisPoolMaxActive;
	}
	public void setRedisPoolMaxActive(int redisPoolMaxActive) {
		this.redisPoolMaxActive = redisPoolMaxActive;
	}
	public int getRedisPoolMaxWait() {
		return redisPoolMaxWait;
	}
	public void setRedisPoolMaxWait(int redisPoolMaxWait) {
		this.redisPoolMaxWait = redisPoolMaxWait;
	}
	public String getRedisHost() {
		return redisHost;
	}
	public void setRedisHost(String redisHost) {
		this.redisHost = redisHost;
	}
	public int getRedisPort() {
		return redisPort;
	}
	public void setRedisPort(int redisPort) {
		this.redisPort = redisPort;
	}
	public int getRedisTimeout() {
		return redisTimeout;
	}
	public void setRedisTimeout(int redisTimeout) {
		this.redisTimeout = redisTimeout;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}	
	
    
	
}
