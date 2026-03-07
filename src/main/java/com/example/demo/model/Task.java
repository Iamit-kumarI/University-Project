package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "tasks")
public class Task {
    @Id private String id;

    private String userId;
    private String semesterId;

    // Core
    private String title;
    private String description;
    private LocalDate date;
    private String color;
    private boolean completed;
    private LocalDateTime completedAt;
    private LocalDateTime createdAt;

    // Priority: LOW / MEDIUM / HIGH / URGENT
    private String priority;

    // Tags e.g. ["math","assignment","exam"]
    private List<String> tags;

    // Checklist items with per-item completion
    private List<SubTask> checklist;

    // Optional due time e.g. "14:30"
    private String dueTime;

    // Subject/course label
    private String subject;
}
