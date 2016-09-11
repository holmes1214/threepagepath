package com.zuora.homework.test;

import java.util.Iterator;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.zuora.homework.threepagepath.data.PageStorage;
import com.zuora.homework.threepagepath.service.impl.PagePathServiceImpl;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class PagePathTest extends HomeWorkTests {

	String[][] sourceData = { { "U1", "/" }, { "U1", "subscribers" }, { "U2", "/" }, { "U2", "subscribers" }, { "U1", "filter" }, { "U1", "export" }, { "U2", "filter" },
			{ "U2", "export" }, { "U3", "/" }, { "U3", "catalog" }, { "U3", "edit" } };

	@Autowired
	private PagePathServiceImpl service;
	@Autowired
	private RedisTemplate<String, String> redis;
	
	@Test
	public void test1ForPageStorage(){
		PageStorage storage=new PageStorage(3);
		storage.push("/");
		Assert.assertNull(storage.getPath());
		storage.push("subscribers");
		Assert.assertNull(storage.getPath());
		storage.push("filter");
		String path = storage.getPath();
		storage.push("export");
		path = storage.getPath();
		Assert.assertNotNull(path);
	}
	
	@Test
	public void test2ForInput(){
		service.collectPage("testUser", "p1");
		service.collectPage("testUser", "p2");
		service.collectPage("testUser", "p3");
		service.collectPage("testUser", "p4");
		Assert.assertTrue(redis.opsForSet().isMember("userset", "testUser"));
		Assert.assertEquals(2, redis.opsForList().size("userdata-testUser").longValue());
	}
	
	@Test
	public void test3ForTopN() {
		for (String[] s : sourceData) {
			service.collectPage(s[0], s[1]);
		}
		Iterable<String> top2 = service.getTopN(2);
		Iterator<String> iterator = top2.iterator();
		String first = iterator.next();
		Assert.assertEquals(first,"/ >> subscribers >> filter");
		String next = iterator.next();
		Assert.assertEquals(next,"subscribers >> filter >> export");
	}

}
