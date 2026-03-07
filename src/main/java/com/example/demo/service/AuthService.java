package com.example.demo.service;
import com.example.demo.dto.*;
import com.example.demo.model.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    private static final List<String> AVATAR_COLORS = List.of(
        "#6366f1","#10b981","#f59e0b","#ec4899","#14b8a6","#ef4444","#f97316","#8b5cf6"
    );

    public AuthResponse register(RegisterRequest req) {
        if (userRepository.existsByEmail(req.getEmail()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already registered");

        User user = new User();
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setAvatarColor(AVATAR_COLORS.get((int)(Math.random() * AVATAR_COLORS.size())));
        user.setCreatedAt(LocalDateTime.now());
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getName(), user.getEmail(), user.getAvatarColor());
    }

    public AuthResponse login(LoginRequest req) {
        User user = userRepository.findByEmail(req.getEmail())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials"));
        if (!passwordEncoder.matches(req.getPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid credentials");
        user.setLastLoginAt(LocalDateTime.now());
        userRepository.save(user);
        return new AuthResponse(jwtUtil.generateToken(user.getEmail()), user.getName(), user.getEmail(), user.getAvatarColor());
    }

    public User getProfile(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    public User updateProfile(String email, UpdateProfileRequest req) {
        User user = getProfile(email);
        user.setName(req.getName());
        if (req.getAvatarColor() != null) user.setAvatarColor(req.getAvatarColor());
        return userRepository.save(user);
    }

    public void changePassword(String email, ChangePasswordRequest req) {
        User user = getProfile(email);
        if (!passwordEncoder.matches(req.getCurrentPassword(), user.getPassword()))
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Current password is incorrect");
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }
}
