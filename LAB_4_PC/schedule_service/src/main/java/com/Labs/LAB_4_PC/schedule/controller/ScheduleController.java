package com.Labs.LAB_4_PC.schedule.controller;

import com.Labs.LAB_4_PC.schedule.entity.Schedule;
import com.Labs.LAB_4_PC.schedule.service.JsonStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final JsonStorageService<Schedule> storageService;

    @Autowired
    public ScheduleController() throws IOException {
        this.storageService = new JsonStorageService<>("src/main/resources/schedules.json", new TypeReference<>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Schedule>> getAll() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Schedule>> add(@RequestBody Schedule schedule) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.save(schedule);
            return ResponseEntity.ok(schedule);
        });
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Schedule>> update(@PathVariable String id, @RequestBody Schedule updated) {
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