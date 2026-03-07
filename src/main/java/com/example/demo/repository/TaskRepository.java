package com.example.demo.repository;
import com.example.demo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserIdAndSemesterId(String userId, String semesterId);
    List<Task> findByUserIdAndSemesterIdAndDate(String userId, String semesterId, LocalDate date);
    List<Task> findByUserId(String userId);
    List<Task> findByUserIdAndDateBetween(String userId, LocalDate start, LocalDate end);
    List<Task> findByUserIdAndCompleted(String userId, boolean completed);
    long countByUserIdAndCompleted(String userId, boolean completed);
    long countByUserId(String userId);
    void deleteByIdAndUserId(String id, String userId);
    void deleteBySemesterId(String semesterId);
}
