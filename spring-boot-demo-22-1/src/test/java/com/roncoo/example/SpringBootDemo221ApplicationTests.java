package com.roncoo.example;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.roncoo.example.component.RoncooAmqpComponent;

@SpringBootTest
public class SpringBootDemo221ApplicationTests {
	@Autowired
	private RoncooAmqpComponent roncooAmqpComponent;

	@Test
	public void send() {
		roncooAmqpComponent.send("hello world2");
	}



}
