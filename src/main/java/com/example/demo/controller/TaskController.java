package com.example.demo.controller;

import com.example.demo.dto.*;
import com.example.demo.model.Task;
import com.example.demo.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/semesters/{semesterId}/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    // GET /api/semesters/{semesterId}/tasks?year=2025&month=9
    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getByMonth(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String semesterId,
            @RequestParam int year,
            @RequestParam int month) {
        List<Task> tasks = taskService.getByMonth(user.getUsername(), semesterId, year, month);
        return ResponseEntity.ok(ApiResponse.ok("Tasks retrieved", tasks));
    }

    // GET /api/semesters/{semesterId}/tasks/date?date=2025-09-03
    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<Task>>> getByDate(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String semesterId,
            @RequestParam LocalDate date) {
        List<Task> tasks = taskService.getByDate(user.getUsername(), semesterId, date);
        return ResponseEntity.ok(ApiResponse.ok("Tasks for date", tasks));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> create(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String semesterId,
            @Valid @RequestBody TaskRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Task created",
                taskService.create(user.getUsername(), semesterId, req)));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Task>> update(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String semesterId,
            @PathVariable String taskId,
            @Valid @RequestBody TaskRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Task updated",
                taskService.update(user.getUsername(), taskId, req)));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String taskId) {
        taskService.delete(user.getUsername(), taskId);
        return ResponseEntity.ok(ApiResponse.ok("Task deleted", null));
    }
}
