package com.Labs.LAB_5_6_PC.vehicle;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class VehicleApplication {

	public static void main(String[] args) {
		SpringApplication.run(VehicleApplication.class, args);
	}

}
