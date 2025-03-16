package com.Labs.LAB_3_PC.entities;

import com.Labs.LAB_3_PC.Identifiable;
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
