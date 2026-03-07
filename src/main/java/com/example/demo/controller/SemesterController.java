package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.model.Semester;
import com.example.demo.service.SemesterService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/semesters")
@RequiredArgsConstructor
public class SemesterController {
    private final SemesterService semesterService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Semester>>> getAll(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.ok("Semesters", semesterService.getAll(u.getUsername())));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Semester>> getOne(@AuthenticationPrincipal UserDetails u, @PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("Semester", semesterService.getById(u.getUsername(), id)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Semester>> create(@AuthenticationPrincipal UserDetails u, @Valid @RequestBody SemesterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Semester created", semesterService.create(u.getUsername(), req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Semester>> update(@AuthenticationPrincipal UserDetails u, @PathVariable String id, @Valid @RequestBody SemesterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Semester updated", semesterService.update(u.getUsername(), id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(@AuthenticationPrincipal UserDetails u, @PathVariable String id) {
        semesterService.delete(u.getUsername(), id);
        return ResponseEntity.ok(ApiResponse.ok("Semester deleted", null));
    }
}
