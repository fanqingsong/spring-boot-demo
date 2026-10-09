package com.roncoo.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.roncoo.example.component.RoncooJmsComponent;

@SpringBootTest
public class SpringBootDemo211ApplicationTests {
	@Autowired
	private RoncooJmsComponent roncooJmsComponent;

	@Test
	public void send() {
		roncooJmsComponent.send("hello world");
	}



}
