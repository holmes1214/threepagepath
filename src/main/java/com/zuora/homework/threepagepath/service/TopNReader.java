package com.zuora.homework.threepagepath.service;
/**
 * 统计结果查询接口
 * @author haozou
 *
 */
public interface TopNReader {

	Iterable<String> getTopN(int n);
}
