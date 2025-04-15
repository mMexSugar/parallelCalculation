package com.Labs.LAB_4_PC.vehicle.entity;

import com.Labs.LAB_4_PC.vehicle.Identifiable;
import lombok.Data;

@Data
public class Vehicle implements Identifiable {
    private String id;
    private String type;     // автобус, трамвай, тролейбус
    private String routeId;  // до якого маршруту призначено

    @Override
    public String getId() {
        return id;
    }
}