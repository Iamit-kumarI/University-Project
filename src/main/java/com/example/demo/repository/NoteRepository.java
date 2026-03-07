package com.example.demo.repository;
import com.example.demo.model.Note;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface NoteRepository extends MongoRepository<Note, String> {
    List<Note> findByUserIdOrderByPinnedDescUpdatedAtDesc(String userId);
    List<Note> findByUserIdAndSemesterIdOrderByPinnedDescUpdatedAtDesc(String userId, String semesterId);
    long countByUserId(String userId);
}
