package com.example.demo.dto;
import com.example.demo.model.SubTask;
import jakarta.validation.constraints.*;
import lombok.Data;
import java.time.LocalDate;
import java.util.List;

@Data
public class TaskRequest {
    @NotBlank private String title;
    private String description;
    @NotNull private LocalDate date;
    private String color;
    private boolean completed;
    private String priority;           // LOW / MEDIUM / HIGH / URGENT
    private List<String> tags;
    private List<SubTask> checklist;   // now with done state
    private String dueTime;
    private String subject;
}
