package com.example.demo.dto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import java.util.List;

@Data
public class NoteRequest {
    @NotBlank private String title;
    private String content;
    private String semesterId;
    private String color;
    private List<String> tags;
    private boolean pinned;
}
