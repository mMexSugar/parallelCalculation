package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Car extends Identifiable {
    private Long id;
    private Long userId;
    private String model;
    private String manufacturer;
    private int year;
    private String licensePlate;

    @Override
    public Long getId() {
        return id;
    }
}