package com.Labs.LAB_3_PC.entities;

import com.Labs.LAB_3_PC.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle implements Identifiable {
    private String id;
    private String type; // Bus, Tram, Trolleybus
    private String routeId; // ID маршруту
    @Override
    public String getId() {
        return id;
    }
}
