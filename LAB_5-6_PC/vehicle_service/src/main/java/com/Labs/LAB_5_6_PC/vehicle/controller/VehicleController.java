package com.Labs.LAB_5_6_PC.vehicle.controller;

import com.Labs.LAB_5_6_PC.vehicle.entity.Vehicle;
import com.Labs.LAB_5_6_PC.vehicle.service.JsonStorageService;
import com.Labs.LAB_5_6_PC.vehicle.service.VehicleValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final JsonStorageService<Vehicle> storageService;
    private final VehicleValidator validator;

    @Autowired
    public VehicleController(VehicleValidator validator) throws IOException {
        this.storageService = new JsonStorageService<>("src/main/resources/vehicles.json", new TypeReference<List<Vehicle>>() {});
        this.validator = validator;
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Vehicle>> getAll() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }
    @Async
    @GetMapping("/{id}")
    @Cacheable(value = "vehicles", key = "#id")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Vehicle vehicle = storageService.getById(id);
        return (vehicle != null) ? ResponseEntity.ok(vehicle) : ResponseEntity.notFound().build();
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<?>> add(@RequestBody Vehicle vehicle) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                validator.validate(vehicle);
                storageService.save(vehicle);
                return ResponseEntity.ok(vehicle);
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
            }
        });
    }

    @Async
    @PutMapping("/{id}")
    @CachePut(value = "vehicles", key = "#route.id")
    public CompletableFuture<ResponseEntity<?>> update(@PathVariable String id, @RequestBody Vehicle updated) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                validator.validate(updated);
                CompletableFuture<Vehicle> v = storageService.update(id, updated);
                return (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build();
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
            }
        });
    }

    @Async
    @DeleteMapping("/{id}")
    @CacheEvict(value = "vehicles", key = "#id")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}