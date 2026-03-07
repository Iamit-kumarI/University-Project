package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@Valid @RequestBody RegisterRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Registered successfully", authService.register(req)));
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@Valid @RequestBody LoginRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Login successful", authService.login(req)));
    }

    @GetMapping("/me")
    public ResponseEntity<ApiResponse<User>> me(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.ok("Profile", authService.getProfile(u.getUsername())));
    }

    @PutMapping("/me")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            @AuthenticationPrincipal UserDetails u,
            @Valid @RequestBody UpdateProfileRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Profile updated", authService.updateProfile(u.getUsername(), req)));
    }

    @PutMapping("/me/password")
    public ResponseEntity<ApiResponse<Void>> changePassword(
            @AuthenticationPrincipal UserDetails u,
            @Valid @RequestBody ChangePasswordRequest req) {
        authService.changePassword(u.getUsername(), req);
        return ResponseEntity.ok(ApiResponse.ok("Password changed", null));
    }
}
