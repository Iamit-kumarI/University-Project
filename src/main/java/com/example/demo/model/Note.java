package com.example.demo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Document(collection = "notes")
public class Note {
    @Id private String id;
    private String userId;
    private String semesterId;  // optional — null = global note
    private String title;
    private String content;
    private String color;
    private List<String> tags;
    private boolean pinned;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
