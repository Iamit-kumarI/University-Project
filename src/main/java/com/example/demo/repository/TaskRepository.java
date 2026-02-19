package com.example.demo.repository;

import com.example.demo.model.Task;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.time.LocalDate;
import java.util.List;

public interface TaskRepository extends MongoRepository<Task, String> {
    List<Task> findByUserIdAndSemesterId(String userId, String semesterId);
    List<Task> findByUserIdAndSemesterIdAndDate(String userId, String semesterId, LocalDate date);
    void deleteByIdAndUserId(String id, String userId);
}
