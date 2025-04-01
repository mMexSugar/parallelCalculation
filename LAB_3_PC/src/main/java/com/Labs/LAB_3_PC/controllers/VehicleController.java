package com.Labs.LAB_3_PC.controllers;

import com.Labs.LAB_3_PC.JsonStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import com.Labs.LAB_3_PC.entities.Vehicle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {
    private final JsonStorageService<Vehicle> storageService;
    @Autowired
    public VehicleController() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("vehicles.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Файл vehicles.json не знайдено");
        }
        //this.storageService = new JsonStorageService<>(inputStream, new TypeReference<>() {});
        this.storageService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_3_PC\\src\\main\\resources\\vehicles.json", new TypeReference<>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<CompletableFuture<List<Vehicle>>> getAllVehicles() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Vehicle>> addVehicle(@RequestBody Vehicle vehicle) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.save(vehicle);
            return ResponseEntity.ok(vehicle);
        });
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Vehicle>> updateVehicle(@PathVariable String id, @RequestBody Vehicle vehicle) {
        return storageService.update(id, vehicle)
                .thenApply(updated -> (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build());

    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteVehicle(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}

