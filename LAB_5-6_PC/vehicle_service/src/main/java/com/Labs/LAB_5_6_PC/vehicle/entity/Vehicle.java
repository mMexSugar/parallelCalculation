package com.Labs.LAB_5_6_PC.vehicle.entity;

import com.Labs.LAB_5_6_PC.vehicle.Identifiable;
import lombok.Data;

@Data
public class Vehicle implements Identifiable {
    private String id;
    private VehicleType type;
    private String model;
    private int capacity;

    @Override
    public String getId() {
        return id;
    }
}