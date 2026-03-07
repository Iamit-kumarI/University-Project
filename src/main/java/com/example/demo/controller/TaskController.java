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

    // All tasks in semester for given month
    @GetMapping
    public ResponseEntity<ApiResponse<List<Task>>> getByMonth(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String semesterId,
            @RequestParam int year, @RequestParam int month) {
        return ResponseEntity.ok(ApiResponse.ok("Tasks", taskService.getByMonth(u.getUsername(), semesterId, year, month)));
    }

    // All tasks in semester (no date filter) — for list view
    @GetMapping("/all")
    public ResponseEntity<ApiResponse<List<Task>>> getAll(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String semesterId) {
        return ResponseEntity.ok(ApiResponse.ok("All tasks", taskService.getAllForSemester(u.getUsername(), semesterId)));
    }

    @GetMapping("/date")
    public ResponseEntity<ApiResponse<List<Task>>> getByDate(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String semesterId,
            @RequestParam LocalDate date) {
        return ResponseEntity.ok(ApiResponse.ok("Tasks for date", taskService.getByDate(u.getUsername(), semesterId, date)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Task>> create(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String semesterId,
            @Valid @RequestBody TaskRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Task created", taskService.create(u.getUsername(), semesterId, req)));
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Task>> update(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String semesterId,
            @PathVariable String taskId,
            @Valid @RequestBody TaskRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Task updated", taskService.update(u.getUsername(), taskId, req)));
    }

    // PATCH toggle complete — no full body needed
    @PatchMapping("/{taskId}/toggle")
    public ResponseEntity<ApiResponse<Task>> toggle(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String taskId) {
        return ResponseEntity.ok(ApiResponse.ok("Task toggled", taskService.toggleComplete(u.getUsername(), taskId)));
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal UserDetails u,
            @PathVariable String taskId) {
        taskService.delete(u.getUsername(), taskId);
        return ResponseEntity.ok(ApiResponse.ok("Task deleted", null));
    }
}
