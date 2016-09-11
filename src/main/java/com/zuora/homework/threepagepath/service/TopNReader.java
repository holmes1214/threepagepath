package com.zuora.homework.threepagepath.service;

public interface TopNReader {

	Iterable<String> getTopN(int n);
}
