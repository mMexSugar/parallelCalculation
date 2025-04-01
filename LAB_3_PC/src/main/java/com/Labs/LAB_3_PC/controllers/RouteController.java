package com.Labs.LAB_3_PC.controllers;

import com.Labs.LAB_3_PC.JsonStorageService;
import com.Labs.LAB_3_PC.entities.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;
import com.fasterxml.jackson.core.type.TypeReference;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/routes")
public class RouteController {
    private final JsonStorageService<Route> storageService;
    @Autowired
    public RouteController() throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("routes.json");
        if (inputStream == null) {
            throw new FileNotFoundException("Файл routes.json не знайдено");
        }
        //this.storageService = new JsonStorageService<>(inputStream, new TypeReference<>() {});
        this.storageService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_3_PC\\src\\main\\resources\\routes.json", new TypeReference<>() {});

    }

    @Async
    @GetMapping
    public CompletableFuture<CompletableFuture<List<Route>>> getAllRoutes() {
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

