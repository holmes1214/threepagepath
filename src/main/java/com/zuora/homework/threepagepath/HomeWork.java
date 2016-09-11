package com.zuora.homework.threepagepath;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;

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
