package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@Document(collection = "semesters")
public class Semester {
    @Id
    private String id;

    private String userId;       // owner
    private String name;         // e.g. "Sem 1"
    private String season;       // e.g. "Fall 2025"
    private String color;        // hex color for icon
    private LocalDate startDate;
    private LocalDate endDate;
}
