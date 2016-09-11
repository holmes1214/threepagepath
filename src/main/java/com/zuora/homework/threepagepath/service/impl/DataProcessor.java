package com.zuora.homework.threepagepath.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import com.zuora.homework.threepagepath.data.PageStorage;
import com.zuora.homework.threepagepath.service.UserLocker;

/**
 * 数据处理类，负责计算用户访问事件的业务并统计
 * @author haozou
 *
 */
@Service
public class DataProcessor {

	protected Logger logger = LoggerFactory.getLogger("zuora.homework");
	
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Autowired
	private UserLocker locker;

	@Value("${inputData.userkey.prefix}")
	private String userPrefix;

	@Value("${inputData.userset.key}")
	private String userSet;

	@Value("${inputData.toplist.key}")
	private String topList;

	@Value("${inputData.pagesize}")
	private int pageSize;

//为了跑过junit测试禁用了异步，生产环境中使用异步调用以提高数据输入接口的相应速度
//	@Async
	public void calculate(String user) {
		logger.info("call calc {}",user);
		//如果该用户被锁，则不统计本次事件，否则就锁住这个用户。使用redis分布式锁是一个比较简单
		//的实现，但是存在安全隐患，生产环境中可以使用zookeeper实现以提高安全性
		locker.lock(user);
		String userKey = userPrefix + user;
		Long size = redis.opsForList().size(userKey);
		if (size >= pageSize) {
			//取出所有未统计的访问事件
			List<String> range = redis.opsForList().range(userKey, 0, -1);
			PageStorage storage = new PageStorage(pageSize);
			//计算需要统计的path
			for (String page : range) {
				storage.push(page);
				String path = storage.getPath();
				if (path != null) {
					logger.debug("path {} incr",path);
					//将需要统计的path+1，因为zset是按照分数正序排列，所以使用负数作为统计值
					redis.opsForZSet().incrementScore(topList, path, -1d);
				}
			}
			//删除所有使用过的事件，但是留下最后的N-1个，如果统计的是连续的3个页面，则留下两个
			redis.opsForList().trim(userKey, range.size() - pageSize+1, -1);
		}else {
			logger.debug("user {} size {}",userKey,size);
		}
		locker.release(userKey);
	}
//定时任务，统计所有有过访问的用户
//	@Scheduled(cron="${inputData.calc.cron}")
	public void calculate(){
		logger.info("run cron {}", System.currentTimeMillis());
		String user=null;
		while((user=redis.opsForSet().pop(userSet))!=null){
			calculate(user);
		}
	}

}
