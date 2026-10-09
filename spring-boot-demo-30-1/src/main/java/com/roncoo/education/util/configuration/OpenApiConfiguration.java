package com.roncoo.education.util.configuration;

import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class OpenApiConfiguration {

	@Bean
	public GroupedOpenApi apiGroup() {
		return GroupedOpenApi.builder()
				.group("api")
				.packagesToScan("com.roncoo.education.controller")
				.pathsToMatch("/api/**")
				.build();
	}

	@Bean
	public OpenAPI openAPI() {
		return new OpenAPI()
				.info(new Info()
						.title("龙果学院")
						.description("spring boot 全集")
						.version("1.0")
						.contact(new Contact().name("wujing").url("http://www.roncoo.com").email("297115770@qq.com")));
	}
}
