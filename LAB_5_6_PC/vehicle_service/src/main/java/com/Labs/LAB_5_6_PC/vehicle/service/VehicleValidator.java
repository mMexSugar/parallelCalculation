package com.Labs.LAB_5_6_PC.vehicle.service;

import com.Labs.LAB_5_6_PC.vehicle.entity.Vehicle;
import com.Labs.LAB_5_6_PC.vehicle.entity.VehicleType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
@Component
public class VehicleValidator {
    private final RestTemplate restTemplate;

    public VehicleValidator(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public void validate(Vehicle vehicle) {
        if (!VehicleType.isValid(String.valueOf(vehicle.getType()))) {
            throw new IllegalArgumentException("Неприпустимий тип транспорту: " + vehicle.getType());
        }

        if (vehicle.getModel() == null || vehicle.getModel().length() < 2) {
            throw new IllegalArgumentException("Модель повинна містити щонайменше 2 символи");
        }

        if (vehicle.getCapacity() < 10 || vehicle.getCapacity() > 200) {
            throw new IllegalArgumentException("Місткість повинна бути від 10 до 200");
        }

    }
}
