package com.Labs.LAB_5_6_PC.routes.entity;

import com.Labs.LAB_5_6_PC.routes.Identifiable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Route implements Identifiable, Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    private String id;
    private String name;
    private String startPoint;
    private String endPoint;
    private List<String> vehicleIds;
    @Override
    public String getId() {
        return id;
    }
}