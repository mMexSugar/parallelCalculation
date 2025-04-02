package com.Labs.LAB_2_PC.controllers;


import com.Labs.LAB_2_PC.JsonStorageService;
import com.Labs.LAB_2_PC.entities.Service;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/services")
public class ServiceController {
    private final JsonStorageService<Service> serviceService;
    @Autowired
    public ServiceController() throws IOException {
        this.serviceService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_2_PC\\src\\main\\resources\\services.json", new TypeReference<List<Service>>() {});
    }

    @GetMapping
    public CompletableFuture<List<Service>> getAllServices() {
        return serviceService.getAll();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Service> getServiceById(@PathVariable Long id) {
        return serviceService.get(id);
    }

    @PostMapping
    public CompletableFuture<Void> createService(@RequestBody Service service) {
        return serviceService.save(service);
    }

    @PutMapping("/{id}")
    public CompletableFuture<Service> updateService(@PathVariable Long id, @RequestBody Service service) {
        service.setId(id);
        return serviceService.update(id, service);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteService(@PathVariable Long id) {
        return serviceService.delete(id);
    }
}
