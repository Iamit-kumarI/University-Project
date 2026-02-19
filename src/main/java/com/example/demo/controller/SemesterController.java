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
    public ResponseEntity<ApiResponse<List<Semester>>> getAll(@AuthenticationPrincipal UserDetails user) {
        List<Semester> semesters = semesterService.getAll(user.getUsername());
        return ResponseEntity.ok(ApiResponse.ok("Semesters retrieved", semesters));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Semester>> create(
            @AuthenticationPrincipal UserDetails user,
            @Valid @RequestBody SemesterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Semester created", semesterService.create(user.getUsername(), req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Semester>> update(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String id,
            @Valid @RequestBody SemesterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Semester updated", semesterService.update(user.getUsername(), id, req)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal UserDetails user,
            @PathVariable String id) {
        semesterService.delete(user.getUsername(), id);
        return ResponseEntity.ok(ApiResponse.ok("Semester deleted", null));
    }
}
