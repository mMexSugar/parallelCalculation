package com.Labs.LAB_3_PC;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@EnableAsync
@SpringBootApplication
public class TransportServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(TransportServiceApplication.class, args);
	}

}