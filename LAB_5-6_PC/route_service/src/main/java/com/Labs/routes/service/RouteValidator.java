package com.Labs.routes.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import com.Labs.routes.entity.Route;

@Component
public class RouteValidator {

    private final RestTemplate restTemplate;

    public RouteValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validate(Route route) {
        if (route.getName() == null || route.getName().isBlank()) {
            throw new IllegalArgumentException("Назва маршруту не може бути порожньою");
        }

        if (route.getVehicleIds() == null) {
            throw new IllegalArgumentException("Список ID транспортних засобів не може бути null (використай порожній список якщо потрібно)");
        }

        for (String vehicleId : route.getVehicleIds()) {
            try {
                String url = "http://localhost:8080/vehicles/" + vehicleId;
                restTemplate.getForObject(url, Object.class);
            } catch (Exception e) {
                throw new IllegalArgumentException("Транспортний засіб з ID " + vehicleId + " не знайдено");
            }
        }
    }
}
