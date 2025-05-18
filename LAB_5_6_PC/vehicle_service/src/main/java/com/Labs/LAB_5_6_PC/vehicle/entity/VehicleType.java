package com.Labs.LAB_5_6_PC.vehicle.entity;

public enum VehicleType {
    BUS, TRAM, TROLLEYBUS;

    public static boolean isValid(String type) {
        try {
            VehicleType.valueOf(type.toUpperCase());
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }
}
