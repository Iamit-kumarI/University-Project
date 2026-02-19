package com.example.demo.repository;

import com.example.demo.model.Semester;
import org.springframework.data.mongodb.repository.MongoRepository;
import java.util.List;

public interface SemesterRepository extends MongoRepository<Semester, String> {
    List<Semester> findByUserId(String userId);
}
