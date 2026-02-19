package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TaskRequest {
    @NotBlank private String title;
    private String description;
    @NotNull private LocalDate date;
    private String color;
    private List<String> checklist;
    private boolean completed;
}
