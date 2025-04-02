package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;

public class Service extends Identifiable {
    private Long id;
    private String name;
    private String description;
    private double price;

    @Override
    public Long getId() {
        return id;
    }
}
