package com.Labs.LAB_2_PC.controllers;

import com.Labs.LAB_2_PC.JsonStorageService;
import com.Labs.LAB_2_PC.entities.User;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/users")
public class UserController {
    private final JsonStorageService<User> userService;
    @Autowired
    public UserController() throws IOException {
        this.userService = new JsonStorageService<>("D:\\проекти Java\\parallelCalculation\\LAB_2_PC\\src\\main\\resources\\users.json", new TypeReference<List<User>>() {});
    }

    @GetMapping
    public CompletableFuture<List<User>> getAllUsers() {
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public CompletableFuture<User> getUserById(@PathVariable Long id) {
        return userService.get(id);
    }

    @PostMapping
    public CompletableFuture<Void> createUser(@RequestBody User user) {
        return userService.save(user);
    }

    @PutMapping("/{id}")
    public CompletableFuture<User> updateUser(@PathVariable Long id, @RequestBody User user) {
        user.setId(id);
        return userService.update(id, user);
    }

    @DeleteMapping("/{id}")
    public CompletableFuture<Void> deleteUser(@PathVariable Long id) {
        return userService.delete(id);
    }
}

