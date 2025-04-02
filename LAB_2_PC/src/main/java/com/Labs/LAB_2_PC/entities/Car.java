package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;

public class Car extends Identifiable {
    private Long id;
    private String model;
    private String manufacturer;
    private int year;

    @Override
    public Long getId() {
        return id;
    }
}