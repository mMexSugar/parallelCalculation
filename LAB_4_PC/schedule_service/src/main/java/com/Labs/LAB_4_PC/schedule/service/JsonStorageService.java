package com.Labs.LAB_4_PC.schedule.service;

import com.Labs.LAB_4_PC.schedule.Identifiable;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.concurrent.CompletableFuture;

public class JsonStorageService<T extends Identifiable> {

    private final File file;
    private final ObjectMapper objectMapper;
    private final TypeReference<List<T>> typeReference;

    public JsonStorageService(String filePath, TypeReference<List<T>> typeReference) {
        this.file = new File(filePath);
        this.objectMapper = new ObjectMapper();
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