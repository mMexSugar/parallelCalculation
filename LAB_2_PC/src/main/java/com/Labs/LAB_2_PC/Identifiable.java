package com.Labs.LAB_2_PC;

public abstract class Identifiable {
    protected Long id;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {  // Додаємо метод
        this.id = id;
    }
}
