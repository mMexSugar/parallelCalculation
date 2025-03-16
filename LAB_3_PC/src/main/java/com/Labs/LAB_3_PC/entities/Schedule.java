package com.Labs.LAB_3_PC.entities;

import com.Labs.LAB_3_PC.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Schedule implements Identifiable {
    private String routeId;
    private List<String> times; // Наприклад: ["08:00", "09:00", "10:00"]

    @Override
    public String getId() {
        return routeId;
    }
}
