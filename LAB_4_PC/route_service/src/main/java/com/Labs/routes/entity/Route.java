package com.Labs.routes.entity;

import com.Labs.routes.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route implements Identifiable {
    private String id;
    private String name;
    private List<String> vehicleIds; // ID призначених транспортних засобів
    @Override
    public String getId() {
        return id;
    }
}