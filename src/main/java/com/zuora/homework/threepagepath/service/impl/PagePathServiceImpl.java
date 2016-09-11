package com.zuora.homework.threepagepath.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zuora.homework.threepagepath.service.DataCollector;
import com.zuora.homework.threepagepath.service.TopNReader;
@Service
public class PagePathServiceImpl implements DataCollector,TopNReader{

	protected Logger logger = LoggerFactory.getLogger("zuora.homework");
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
		logger.info("top {}",n);
		return redis.opsForZSet().range(topList, 0, n-1);
	}

	@Override
	public void collectPage(String user, String page) {
		logger.info("user {} visit page {}",user,page);
		String userKey=userPrefix+user;
		redis.opsForList().rightPush(userKey, page);
		redis.opsForSet().add(userSet, user);
		processor.calculate(user);
	}

}
