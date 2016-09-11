package com.zuora.homework.threepagepath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 使用redis作为数据存储，排序，分布式锁和事件消息中间件。可以使用两种方式计算数据，一种是在输入数据
 * 时异步调用统计方法进行统计，每一次输入事件都会触发一次统计，但是针对单个用户同一时刻只会有一个
 * 线程进行统计，此方式对系统开销比较大，但是实时性比较高；第二种方式是使用定时任务方式进行统计，
 * 统计任务会在系统配置的cron 触发时启动，对需要统计的用户（在任务周期内有过访问事件）进行统计
 * 
 * 数据存储使用redis，每个用户的访问事件会被push到redis的一个列表中，而统计事件会消费这个列表，
 * 所有的历史数据在统计完成后销毁，不会占用更多的内存资源
 * @author haozou
 *
 */
@SpringBootApplication(scanBasePackages="com.zuora")
@EnableScheduling
@EnableAsync
public class HomeWork {

	public static void main(String[] args) {
		SpringApplication application = new SpringApplication(HomeWork.class);
        application.setWebEnvironment(false);
        application.run(args);
	}
}
