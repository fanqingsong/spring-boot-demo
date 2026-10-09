package com.roncoo.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.roncoo.example.component.RoncooRedisComponent;

@SpringBootTest
public class SpringBootDemo171ApplicationTests {

	@Autowired
	private RoncooRedisComponent roncooRedisComponent;

	@Test
	public void set() {
		roncooRedisComponent.set("roncoo1", "hello world");
	}

	@Test
	public void get() {
		System.out.println(roncooRedisComponent.get("roncoo"));
	}

	@Test
	public void del() {
		roncooRedisComponent.del("roncoo1");
	}

}
