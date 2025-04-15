package com.Labs.routes.controller;

import com.Labs.routes.entity.Route;
import com.Labs.routes.service.JsonStorageService;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/routes")
public class RouteController {

    private final JsonStorageService<Route> storageService;

    @Autowired
    public RouteController() throws IOException {
        this.storageService = new JsonStorageService<Route>("src/main/resources/routes.json", new TypeReference<List<Route>>() {});
    }

    @Async
    @GetMapping
    public CompletableFuture<List<Route>> getAllRoutes() {
        return CompletableFuture.supplyAsync(storageService::getAll);
    }

    @Async
    @PostMapping
    public CompletableFuture<ResponseEntity<Route>> addRoute(@RequestBody Route route) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.save(route);
            return ResponseEntity.ok(route);
        });
    }

    @Async
    @PutMapping("/{id}")
    public CompletableFuture<ResponseEntity<Route>> updateRoute(@PathVariable String id, @RequestBody Route updatedRoute) {
        return storageService.update(id, updatedRoute)
                .thenApply(updated -> (updated != null) ? ResponseEntity.ok(updated) : ResponseEntity.notFound().build());
    }

    @Async
    @DeleteMapping("/{id}")
    public CompletableFuture<ResponseEntity<Void>> deleteRoute(@PathVariable String id) {
        return CompletableFuture.supplyAsync(() -> {
            storageService.delete(id);
            return ResponseEntity.noContent().build();
        });
    }
}