package com.Labs.LAB_4_PC.vehicle.controller;

import com.Labs.LAB_4_PC.vehicle.entity.Vehicle;
import com.Labs.LAB_4_PC.vehicle.service.JsonStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final JsonStorageService<Vehicle> storageService;

    @Autowired
    public VehicleController() throws IOException {
        this.storageService = new JsonStorageService<>("src/main/resources/vehicles.json", new TypeReference<List<Vehicle>>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Vehicle>> getAll() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Vehicle>> add(@RequestBody Vehicle vehicle) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.save(vehicle);
            return ResponseEntity.ok(vehicle);
        });
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Vehicle>> update(@PathVariable String id, @RequestBody Vehicle updated) {
        return storageService.update(id, updated)
                .thenApply(v -> (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build());
    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}