package com.zuora.homework.threepagepath.data;

public class PageStorage {
	String[] data;
	int index=0;
	final int size;
	static final String SEPARATOR=" >> ";
	public PageStorage(int n){
		size=n;
		data=new String[n];
	}
	
	
	public void push(String page){
		data[index%size]=page;
		index++;
	}
	
	public String getPath(){
		if (index<size) {
			return null;
		}
		StringBuilder result=new StringBuilder();
		for(int i=0;i<size;i++){
			result.append(data[(index+i)%size]);
			result.append(SEPARATOR);
		}
		return result.substring(0, result.length()-SEPARATOR.length());
	}
	
}
