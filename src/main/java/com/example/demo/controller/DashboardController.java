package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.service.DashboardService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
public class DashboardController {
    private final DashboardService dashboardService;

    @GetMapping
    public ResponseEntity<ApiResponse<DashboardStats>> getStats(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.ok("Dashboard stats", dashboardService.getStats(u.getUsername())));
    }
}
