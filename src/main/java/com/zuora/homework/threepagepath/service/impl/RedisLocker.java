package com.zuora.homework.threepagepath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zuora.homework.threepagepath.service.UserLocker;

@Service
public class RedisLocker implements UserLocker{

	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Value("${inputData.userkey.lock}")
	private String userLock;
	
	@Override
	public boolean lock(String user) {
		if (redis.opsForSet().isMember(userLock, user)) {
			return false;
		}else {
			redis.opsForSet().add(userLock, user);
		}
		return true;
	}

	@Override
	public void release(String user) {
		redis.opsForSet().remove(userLock, user);
	}

}
