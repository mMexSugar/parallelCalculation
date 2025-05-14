package com.Labs.LAB_5_6_PC.schedule.service;

import com.Labs.LAB_5_6_PC.schedule.Identifiable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class JsonStorageService<T extends Identifiable> {

    private final File file;
    private final ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    private final TypeReference<List<T>> typeReference;

    public JsonStorageService(String filePath, TypeReference<List<T>> typeReference) {
        this.file = new File(filePath);
        //this.objectMapper = new ObjectMapper();
        this.typeReference = typeReference;
    }

    public List<T> getAll() {
        try {
            if (!file.exists()) return new ArrayList<>();
            return objectMapper.readValue(file, typeReference);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public T getById(String id) {
        return getAll().stream()
                .filter(item -> id.equals(item.getId()))
                .findFirst()
                .orElse(null);
    }

    public void save(T obj) {
        List<T> list = getAll();
        list.add(obj);
        writeList(list);
    }

    public CompletableFuture<T> update(String id, T updated) {
        return CompletableFuture.supplyAsync(() -> {
            List<T> list = getAll();
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).getId().equals(id)) {
                    list.set(i, updated);
                    writeList(list);
                    return updated;
                }
            }
            return null;
        });
    }

    public void delete(String id) {
        List<T> list = getAll();
        list.removeIf(obj -> obj.getId().equals(id));
        writeList(list);
    }

    private void writeList(List<T> list) {
        try {
            objectMapper.writerWithDefaultPrettyPrinter().writeValue(file, list);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}