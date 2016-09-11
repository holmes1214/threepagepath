package com.zuora.homework.threepagepath.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.zuora.homework.threepagepath.data.PageStorage;


@Service
public class DataProcessor {

	@Autowired
	private RedisTemplate<String, String> redis;

	@Value("${inputData.userkey.prefix}")
	private String userPrefix;

	@Value("${inputData.userset.key}")
	private String userSet;

	@Value("${inputData.toplist.key}")
	private String topList;

	@Value("${inputData.pagesize}")
	private int pageSize;

	@Async
	public void calculate(String user) {
		String userKey = userPrefix + user;
		Long size = redis.opsForList().size(userKey);
		if (size > 3) {
			List<String> range = redis.opsForList().range(userKey, 0, -1);
			PageStorage storage = new PageStorage(pageSize);
			for (String page : range) {
				storage.push(page);
				String path = storage.getPath();
				if (path != null) {
					redis.opsForZSet().incrementScore(topList, path, -1d);
				}
			}
			redis.opsForList().trim(userKey, range.size() - pageSize, -1);
		}
	}

	@Scheduled(cron="${inputData.calc.cron}")
	public void calculate(){
		String user=null;
		while((user=redis.opsForSet().pop(userSet))!=null){
			calculate(user);
		}
	}

}
