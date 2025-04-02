package com.Labs.LAB_2_PC.controllers;


import com.Labs.LAB_2_PC.JsonStorageService;
import com.Labs.LAB_2_PC.entities.Appointment;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/appointments")
public class AppointmentController {
    private final JsonStorageService<Appointment> appointmentService;
    @Autowired
    public AppointmentController() throws IOException {
        this.appointmentService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_2_PC\\src\\main\\resources\\appointments.json", new TypeReference<List<Appointment>>() {});
    }

    @GetMapping
    public CompletableFuture<List<Appointment>> getAllAppointments() {
        return appointmentService.getAll();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Appointment> getAppointmentById(@PathVariable Long id) {
        return appointmentService.get(id);
    }

    @PostMapping
    public CompletableFuture<Void> createAppointment(@RequestBody Appointment appointment) {
        return appointmentService.save(appointment);
    }

    @PutMapping("/{id}")
    public CompletableFuture<Appointment> updateAppointment(@PathVariable Long id, @RequestBody Appointment appointment) {
        appointment.setId(id);
        return appointmentService.update(id, appointment);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteAppointment(@PathVariable Long id) {
        return appointmentService.delete(id);
    }
}
