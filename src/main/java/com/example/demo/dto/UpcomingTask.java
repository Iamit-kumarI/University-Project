package com.example.demo.dto;
import lombok.AllArgsConstructor;
import lombok.Data;
import java.time.LocalDate;

@Data @AllArgsConstructor
public class UpcomingTask {
    private String id;
    private String title;
    private String subject;
    private String priority;
    private String color;
    private LocalDate date;
    private String dueTime;
    private String semesterName;
}
