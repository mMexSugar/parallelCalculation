package com.Labs.LAB_2_PC.controllers;


import com.Labs.LAB_2_PC.JsonStorageService;
import com.Labs.LAB_2_PC.entities.Car;
import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final JsonStorageService<Car> carService;
    @Autowired
    public CarController() throws IOException {
        this.carService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_2_PC\\src\\main\\resources\\cars.json", new TypeReference<List<Car>>() {});
    }

    @GetMapping
    public CompletableFuture<List<Car>> getAllCars() {
        return carService.getAll();
    }

    @GetMapping("/{id}")
    public CompletableFuture<Car> getCarById(@PathVariable Long id) {
        return carService.get(id);
    }

    @PostMapping
    public CompletableFuture<Void> createCar(@RequestBody Car car) {
        return carService.save(car);
    }

    @PutMapping("/{id}")
    public CompletableFuture<Car> updateCar(@PathVariable Long id, @RequestBody Car car) {
        car.setId(id);
        return carService.update(id, car);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteCar(@PathVariable Long id) {
        return carService.delete(id);
    }
}
