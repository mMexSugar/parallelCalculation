package com.Labs.LAB_4_PC.schedule.entity;

import com.Labs.LAB_4_PC.schedule.Identifiable;
import lombok.Data;

@Data
public class Schedule implements Identifiable {
    private String id;
    private String routeId;
    private String interval; // наприклад, "07:00-10:00"
    private String frequency; // наприклад, "кожні 15 хвилин"

    @Override
    public String getId() {
        return id;
    }
}