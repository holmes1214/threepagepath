package com.zuora.homework.threepagepath.service;
/**
 * 分布式锁接口，避免某个用户在统计的过程中，另一个线程又开始进行统计
 * @author haozou
 *
 */
public interface UserLocker {

	boolean lock(String user);
	
	void release(String user);
}
