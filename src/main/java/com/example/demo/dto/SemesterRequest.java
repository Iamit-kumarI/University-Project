package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.time.LocalDate;

@Data
public class SemesterRequest {
    @NotBlank private String name;
    @NotBlank private String season;
    private String color;
    private LocalDate startDate;
    private LocalDate endDate;
}
