package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment extends Identifiable {
    private Long id;
    private Long carId;
    private Long userId;
    private Long serviceId;
    private LocalDate date;
    private LocalTime time;

    @Override
    public Long getId() {
        return id;
    }
}
