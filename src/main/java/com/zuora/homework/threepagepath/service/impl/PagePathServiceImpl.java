package com.zuora.homework.threepagepath.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zuora.homework.threepagepath.service.DataCollector;
import com.zuora.homework.threepagepath.service.TopNReader;
@Service
public class PagePathServiceImpl implements DataCollector,TopNReader{

	@Autowired
	private DataProcessor processor;
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Value("${inputData.userkey.prefix}")
	private String userPrefix;

	@Value("${inputData.userset.key}")
	private String userSet;

	@Value("${inputData.toplist.key}")
	private String topList;
	
	@Override
	public Iterable<String> getTopN(int n) {
		return redis.opsForZSet().range(topList, 0, n-1);
	}

	@Override
	public void collectPage(String user, String page) {
		String userKey=userPrefix+user;
		redis.opsForList().rightPush(userKey, page);
		processor.calculate(userKey);
	}

}
