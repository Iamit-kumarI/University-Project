package com.example.demo.service;
import com.example.demo.dto.SemesterRequest;
import com.example.demo.model.Semester;
import com.example.demo.repository.SemesterRepository;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {
    private final SemesterRepository semesterRepository;
    private final TaskRepository taskRepository;

    public List<Semester> getAll(String userId) {
        return semesterRepository.findByUserId(userId);
    }

    public Semester getById(String userId, String semesterId) {
        return semesterRepository.findById(semesterId)
                .filter(s -> s.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semester not found"));
    }

    public Semester create(String userId, SemesterRequest req) {
        Semester s = new Semester();
        s.setUserId(userId);
        s.setName(req.getName());
        s.setSeason(req.getSeason());
        s.setColor(req.getColor() != null ? req.getColor() : "#6366f1");
        s.setStartDate(req.getStartDate());
        s.setEndDate(req.getEndDate());
        s.setCreatedAt(LocalDateTime.now());
        return semesterRepository.save(s);
    }

    public Semester update(String userId, String semesterId, SemesterRequest req) {
        Semester s = getById(userId, semesterId);
        s.setName(req.getName());
        s.setSeason(req.getSeason());
        if (req.getColor() != null) s.setColor(req.getColor());
        if (req.getStartDate() != null) s.setStartDate(req.getStartDate());
        if (req.getEndDate() != null) s.setEndDate(req.getEndDate());
        return semesterRepository.save(s);
    }

    public void delete(String userId, String semesterId) {
        Semester s = getById(userId, semesterId);
        taskRepository.deleteBySemesterId(semesterId);  // cascade delete tasks
        semesterRepository.delete(s);
    }
}
