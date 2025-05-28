package com.Labs.LAB_5_6_PC.schedule.controller;

import com.Labs.LAB_5_6_PC.schedule.entity.Schedule;
import com.Labs.LAB_5_6_PC.schedule.service.JsonStorageService;
import com.Labs.LAB_5_6_PC.schedule.service.ScheduleValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/schedules")
public class ScheduleController {

    private final JsonStorageService<Schedule> storageService;
    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    private final ScheduleValidator validator;

    @Autowired
    public ScheduleController(ScheduleValidator validator) throws IOException {
        this.validator = validator;
        this.storageService = new JsonStorageService<>("src/main/resources/schedules.json", new TypeReference<List<Schedule>>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Schedule>> getAll() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }


    @Async
    @GetMapping("/{id}")
    @Cacheable(value = "schedules", key = "#id")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Schedule schedule = storageService.getById(id);
        return (schedule != null) ? ResponseEntity.ok(schedule) : ResponseEntity.notFound().build();
    }

    @Async
    @PostMapping
    public ResponseEntity<?> add(@RequestBody Schedule schedule) {
        try {
            validator.validate(schedule);
            storageService.save(schedule);
            return ResponseEntity.ok(schedule);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @PutMapping("/{id}")
    @CachePut(value = "schedules", key = "#route.id")
    public ResponseEntity<?> update(@PathVariable String id, @RequestBody Schedule updated) {
        try {
            validator.validate(updated);
            return storageService.update(id, updated)
                    .thenApply(v -> (v != null) ? ResponseEntity.ok(v) : ResponseEntity.notFound().build())
                    .join();
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @Async
    @DeleteMapping("/{id}")
    @CacheEvict(value = "schedules", key = "#id")
    public CompletableFuture<ResponseEntity<Void>> delete(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}