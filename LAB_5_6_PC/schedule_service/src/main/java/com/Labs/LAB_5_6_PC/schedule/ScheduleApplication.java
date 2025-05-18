package com.Labs.LAB_5_6_PC.schedule;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
@EnableCaching
public class ScheduleApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScheduleApplication.class, args);
	}

	@Bean
	public Jackson2ObjectMapperBuilderCustomizer jsr310Customizer() {
		return builder -> builder
				.modules(new JavaTimeModule())
				.featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
	}

}
