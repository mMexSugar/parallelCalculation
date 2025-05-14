package com.Labs.LAB_5_6_PC.schedule.entity;

import com.Labs.LAB_5_6_PC.schedule.Identifiable;
import lombok.Data;

import java.time.LocalTime;

@Data
public class Schedule implements Identifiable {
    private String id;
    private String routeId;
    private LocalTime startTime;     // Наприклад: 07:00
    private LocalTime endTime;       // Наприклад: 10:00
    private int frequencyMinutes; // наприклад, "кожні 15 хвилин"

    @Override
    public String getId() {
        return id;
    }
}