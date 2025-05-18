package com.Labs.LAB_5_6_PC.routes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class RouteApplication {

	public static void main(String[] args) {
		SpringApplication.run(RouteApplication.class, args);
	}

}
