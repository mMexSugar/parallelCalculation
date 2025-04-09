package com.Labs.LAB_2_PC.entities;

import com.Labs.LAB_2_PC.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Identifiable {
    private Long id;
    private String name;
    private String email;

    @Override
    public Long getId() {
        return id;
    }
}