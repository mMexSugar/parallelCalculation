package com.Labs.LAB_3_PC.controllers;

import com.Labs.LAB_3_PC.JsonStorageService;
import com.Labs.LAB_3_PC.entities.Route;
import com.fasterxml.jackson.core.type.TypeReference;
import com.Labs.LAB_3_PC.entities.Schedule;
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
@RequestMapping("/schedules")
public class ScheduleController {
    private final JsonStorageService<Schedule> storageService;
    @Autowired
    public ScheduleController() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("schedules.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Файл schedules.json не знайдено");
        }
        this.storageService = new JsonStorageService<>(inputStream, new TypeReference<>() {});
    }

    @Async
    @GetMapping("/{routeId}")
    public CompletableFuture<ResponseEntity<Schedule>> getSchedule(@PathVariable String routeId) {
        return storageService.get(routeId)
                .thenApply(schedule -> (schedule != null) ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build());
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Schedule>> addSchedule(@RequestBody Schedule schedule) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.save(schedule);
            return ResponseEntity.ok(schedule);
        });
    }

    @Async
    @PutMapping("/{routeId}")
    public CompletableFuture<ResponseEntity<Schedule>> updateSchedule(@PathVariable String routeId, @RequestBody Schedule updatedSchedule) {
        return storageService.update(routeId, updatedSchedule)
                .thenApply(updated -> (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build());

    }

    @Async
    @DeleteMapping("/{routeId}")
    public CompletableFuture<ResponseEntity<Void>> deleteSchedule(@PathVariable String routeId) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(routeId);
            return ResponseEntity.noContent().build();
        });
    }
}

