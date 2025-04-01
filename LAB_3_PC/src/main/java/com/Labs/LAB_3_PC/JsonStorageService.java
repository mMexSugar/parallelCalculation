package com.Labs.LAB_3_PC;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

public class JsonStorageService<T extends Identifiable> {
    private final List<T> data;
    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<List<T>> typeReference;

        public JsonStorageService(String filePath, TypeReference<List<T>> typeReference) throws IOException {
        this.filePath = filePath;
        this.typeReference = typeReference;
        File file = new File(filePath);
        if (file.exists()) {
            try (InputStream inputStream = new FileInputStream(file)) {
                this.data = objectMapper.readValue(inputStream, typeReference);
            }
        } else {
            this.data = new ArrayList<>();
            saveData();  // Створити файл якщо не існує
        }

    }

    public CompletableFuture<List<T>> getAll() {
        return CompletableFuture.supplyAsync(() -> new ArrayList<>(data));
    }

    public CompletableFuture<T> get(String id) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(id)) {
                    return data.get(i);
                }
            }
            return null;
        });
    }

    public CompletableFuture<Void> save(T entity) {
        return CompletableFuture.runAsync(() -> {
            data.add(entity);
            saveData();
        });
    }

    public CompletableFuture<T> update(String id, T updatedEntity) {
        return CompletableFuture.supplyAsync(() -> {
            for (int i = 0; i < data.size(); i++) {
                if (data.get(i).getId().equals(id)) {
                    data.set(i, updatedEntity);
                    saveData();
                    return updatedEntity;
                }
            }
            return null;
        });
    }

    public CompletableFuture<Void> delete(String id) {
        return CompletableFuture.runAsync(() -> {
            data.removeIf(entity -> entity.getId().equals(id));
            saveData();
        });
    }

    private void saveData() {
        try (BufferedWriter writer = Files.newBufferedWriter(Path.of(filePath))) {
            objectMapper.writeValue(writer, data);
        } catch (IOException e) {
            throw new RuntimeException("Помилка збереження даних у JSON", e);
        }
    }
}

