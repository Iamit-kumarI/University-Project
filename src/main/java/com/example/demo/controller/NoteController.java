package com.example.demo.controller;
import com.example.demo.dto.*;
import com.example.demo.model.Note;
import com.example.demo.service.NoteService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/notes")
@RequiredArgsConstructor
public class NoteController {
    private final NoteService noteService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<Note>>> getAll(@AuthenticationPrincipal UserDetails u) {
        return ResponseEntity.ok(ApiResponse.ok("Notes", noteService.getAll(u.getUsername())));
    }

    @GetMapping("/semester/{semesterId}")
    public ResponseEntity<ApiResponse<List<Note>>> getBySemester(
            @AuthenticationPrincipal UserDetails u, @PathVariable String semesterId) {
        return ResponseEntity.ok(ApiResponse.ok("Notes for semester", noteService.getBySemester(u.getUsername(), semesterId)));
    }

    @PostMapping
    public ResponseEntity<ApiResponse<Note>> create(
            @AuthenticationPrincipal UserDetails u, @Valid @RequestBody NoteRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Note created", noteService.create(u.getUsername(), req)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<Note>> update(
            @AuthenticationPrincipal UserDetails u, @PathVariable String id, @Valid @RequestBody NoteRequest req) {
        return ResponseEntity.ok(ApiResponse.ok("Note updated", noteService.update(u.getUsername(), id, req)));
    }

    @PatchMapping("/{id}/pin")
    public ResponseEntity<ApiResponse<Note>> togglePin(
            @AuthenticationPrincipal UserDetails u, @PathVariable String id) {
        return ResponseEntity.ok(ApiResponse.ok("Note pin toggled", noteService.togglePin(u.getUsername(), id)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> delete(
            @AuthenticationPrincipal UserDetails u, @PathVariable String id) {
        noteService.delete(u.getUsername(), id);
        return ResponseEntity.ok(ApiResponse.ok("Note deleted", null));
    }
}
