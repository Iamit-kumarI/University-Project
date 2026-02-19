package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;
import java.util.List;

@Data
@Document(collection = "tasks")
public class Task {
    @Id
    private String id;

    private String userId;
    private String semesterId;
    private String title;
    private String description;
    private LocalDate date;
    private String color;           // hex or named color
    private List<String> checklist; // checklist items
    private boolean completed;
}
