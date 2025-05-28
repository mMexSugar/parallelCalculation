package com.Labs.LAB_5_6_PC.routes.service;

import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.Labs.LAB_5_6_PC.routes.entity.Route;

@Component
public class RouteValidator {

    private final RestTemplate restTemplate;
    private final String urlVehicles = "http://localhost:8082/vehicles/";

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
                String url = urlVehicles + vehicleId;
                restTemplate.getForObject(url, Object.class);
            } catch (Exception e) {
                throw new IllegalArgumentException("Транспортний засіб з ID " + vehicleId + " не знайдено");
            }
        }
    }
}
