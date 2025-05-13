package com.Labs.routes.controller;

import com.Labs.routes.entity.Route;
import com.Labs.routes.service.JsonStorageService;
import com.Labs.routes.service.RouteValidator;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final JsonStorageService<Route> storageService;
    private final RouteValidator validator;

    @Autowired
    public RouteController(RouteValidator validator) throws IOException {
        this.validator = validator;
        this.storageService = new JsonStorageService<Route>("src/main/resources/routes.json", new TypeReference<List<Route>>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Route>> getAllRoutes() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable("id") String id) {
        Route route = storageService.getById(id);
        return (route != null) ? ResponseEntity.ok(route) : ResponseEntity.notFound().build();
    }

    @Async
    @PostMapping
    public ResponseEntity<?> addRoute(@RequestBody Route route) {
        try {
            validator.validate(route);
            storageService.save(route);
            return ResponseEntity.ok(route);
        } catch (IllegalArgumentException ex) {
            return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
        }
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<?>> update(@PathVariable("id") String id, @RequestBody Route updated) {
        return CompletableFuture.supplyAsync(() -> {
            try {
                validator.validate(updated);
                return storageService.update(id, updated)
                        .thenApply(route -> (route != null)
                                ? ResponseEntity.ok(route)
                                : ResponseEntity.notFound().build())
                        .join(); // дочекайся завершення async update
            } catch (IllegalArgumentException ex) {
                return ResponseEntity.badRequest().body(Map.of("error", ex.getMessage()));
            }
        });
    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteRoute(@PathVariable("id") String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}