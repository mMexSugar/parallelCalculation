package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;

import java.time.LocalDateTime;

public class Appointment extends Identifiable {
    private Long id;
    private Long carId;
    private Long userId;
    private Long serviceId;
    private LocalDateTime appointmentTime;

    @Override
    public Long getId() {
        return id;
    }
}
