package com.example.demo.service;
import com.example.demo.dto.NoteRequest;
import com.example.demo.model.Note;
import com.example.demo.repository.NoteRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class NoteService {
    private final NoteRepository noteRepository;

    public List<Note> getAll(String userId) {
        return noteRepository.findByUserIdOrderByPinnedDescUpdatedAtDesc(userId);
    }

    public List<Note> getBySemester(String userId, String semesterId) {
        return noteRepository.findByUserIdAndSemesterIdOrderByPinnedDescUpdatedAtDesc(userId, semesterId);
    }

    public Note create(String userId, NoteRequest req) {
        Note note = new Note();
        note.setUserId(userId);
        note.setSemesterId(req.getSemesterId());
        note.setTitle(req.getTitle());
        note.setContent(req.getContent());
        note.setColor(req.getColor() != null ? req.getColor() : "#6366f1");
        note.setTags(req.getTags());
        note.setPinned(req.isPinned());
        note.setCreatedAt(LocalDateTime.now());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note update(String userId, String noteId, NoteRequest req) {
        Note note = getOwned(userId, noteId);
        note.setTitle(req.getTitle());
        note.setContent(req.getContent());
        if (req.getColor() != null) note.setColor(req.getColor());
        note.setTags(req.getTags());
        note.setPinned(req.isPinned());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public Note togglePin(String userId, String noteId) {
        Note note = getOwned(userId, noteId);
        note.setPinned(!note.isPinned());
        note.setUpdatedAt(LocalDateTime.now());
        return noteRepository.save(note);
    }

    public void delete(String userId, String noteId) {
        noteRepository.delete(getOwned(userId, noteId));
    }

    private Note getOwned(String userId, String noteId) {
        return noteRepository.findById(noteId)
                .filter(n -> n.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Note not found"));
    }
}
