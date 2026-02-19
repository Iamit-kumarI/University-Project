package com.example.demo.service;

import com.example.demo.dto.SemesterRequest;
import com.example.demo.model.Semester;
import com.example.demo.repository.SemesterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SemesterService {

    private final SemesterRepository semesterRepository;

    public List<Semester> getAll(String userId) {
        return semesterRepository.findByUserId(userId);
    }

    public Semester create(String userId, SemesterRequest req) {
        Semester s = new Semester();
        s.setUserId(userId);
        s.setName(req.getName());
        s.setSeason(req.getSeason());
        s.setColor(req.getColor() != null ? req.getColor() : "#7C3AED");
        s.setStartDate(req.getStartDate());
        s.setEndDate(req.getEndDate());
        return semesterRepository.save(s);
    }

    public Semester update(String userId, String semesterId, SemesterRequest req) {
        Semester s = semesterRepository.findById(semesterId)
                .filter(sem -> sem.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semester not found"));
        s.setName(req.getName());
        s.setSeason(req.getSeason());
        if (req.getColor() != null) s.setColor(req.getColor());
        if (req.getStartDate() != null) s.setStartDate(req.getStartDate());
        if (req.getEndDate() != null) s.setEndDate(req.getEndDate());
        return semesterRepository.save(s);
    }

    public void delete(String userId, String semesterId) {
        Semester s = semesterRepository.findById(semesterId)
                .filter(sem -> sem.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Semester not found"));
        semesterRepository.delete(s);
    }
}
