package com.roncoo.example;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.server.servlet.context.ServletComponentScan;

@ServletComponentScan
@SpringBootApplication
public class SpringBootDemo161Application {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemo161Application.class, args);
	}
}
