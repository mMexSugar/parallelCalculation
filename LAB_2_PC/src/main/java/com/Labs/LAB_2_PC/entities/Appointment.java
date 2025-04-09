package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
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
