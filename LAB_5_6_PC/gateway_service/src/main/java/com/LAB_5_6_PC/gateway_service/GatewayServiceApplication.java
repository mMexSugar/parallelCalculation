package com.LAB_5_6_PC.gateway_service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GatewayServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(GatewayServiceApplication.class, args);
	}

	@Bean
	public RouteLocator customRouteLocator(RouteLocatorBuilder builder) {
//		return builder.routes()
//				.route("route_service", r -> r.path("/routes/**").uri("http://localhost:8081"))
//				.route("vehicle_service", r -> r.path("/vehicles/**").uri("http://localhost:8082"))
//				.route("schedule_service", r -> r.path("/schedules/**").uri("http://localhost:8083"))
//				.build();
		return builder.routes()
				.route("route_service", r -> r.path("/routes/**")
						.filters(f -> f.setRequestHeader("Host", "localhost")).uri("http://route_service:8081"))
				.route("vehicle_service", r -> r.path("/vehicles/**")
						.filters(f -> f.setRequestHeader("Host", "localhost"))
						.uri("http://vehicle_service:8082"))
				.route("schedule_service", r -> r.path("/schedules/**")
						.filters(f -> f.setRequestHeader("Host", "localhost")).uri("http://schedule_service:8083"))
				.build();
	}


}
