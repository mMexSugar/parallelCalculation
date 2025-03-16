package com.Labs.LAB_3_PC;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

//public class JsonStorageService<T extends Identifiable> {
//    private final String filePath;
//    private final ObjectMapper objectMapper = new ObjectMapper();
//    private final List<T> data;
//    private final TypeReference<List<T>> typeReference;
//
//    public JsonStorageService(String filePath, TypeReference<List<T>> typeReference) {
//        this.filePath = filePath;
//        this.typeReference = typeReference;
//        this.data = loadData();
//    }
//
//    public JsonStorageService(InputStream inputStream, TypeReference<List<T>> typeReference) throws IOException {
//        this.filePath = null;
//        this.typeReference = typeReference;
//        this.data = loadData(inputStream);
//    }
//
//    private List<T> loadData() {
//        try {
//            byte[] jsonData = Files.readAllBytes(Paths.get(filePath));
//            return objectMapper.readValue(jsonData, typeReference);
//        } catch (IOException e) {
//            return new ArrayList<>();
//        }
//    }
//
//    public List<T> loadData(InputStream inputStream) throws IOException {
//        return objectMapper.readValue(inputStream, typeReference);
//    }
//
//    private void saveData() {
//        if (filePath != null) {
//            try {
//                objectMapper.writeValue(new File(filePath), data);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        } else {
//            System.out.println("Збереження неможливе: файл із ресурсів доступний лише для читання.");
//        }
//    }
//
//    public List<T> getAll() {
//        return data;
//    }
//
//    public void save(T entity) {
//        data.add(entity);
//        saveData();
//    }
//
//    public T update(String id, T updatedEntity) {
//        for (int i = 0; i < data.size(); i++) {
//            if (data.get(i).getId().equals(id)) {
//                data.set(i, updatedEntity);
//                saveData();
//                return updatedEntity;
//            }
//        }
//        return null;
//    }
//
//    public void delete(String id) {
//        data.removeIf(entity -> entity.getId().equals(id));
//        saveData();
//    }
//}

public class JsonStorageService<T extends Identifiable> {
    private final List<T> data;
    private final String filePath;
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final TypeReference<List<T>> typeReference;

        public JsonStorageService(InputStream inputStream, TypeReference<List<T>> typeReference) throws IOException {
        this.filePath = null;
        this.typeReference = typeReference;
        this.data = objectMapper.readValue(inputStream, typeReference);
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

