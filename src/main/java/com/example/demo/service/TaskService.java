package com.example.demo.service;

import com.example.demo.dto.TaskRequest;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    public List<Task> getByMonth(String userId, String semesterId, int year, int month) {
        // Return all tasks in semester, filter by year+month
        return taskRepository.findByUserIdAndSemesterId(userId, semesterId)
                .stream()
                .filter(t -> t.getDate() != null
                        && t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .toList();
    }

    public List<Task> getByDate(String userId, String semesterId, LocalDate date) {
        return taskRepository.findByUserIdAndSemesterIdAndDate(userId, semesterId, date);
    }

    public Task create(String userId, String semesterId, TaskRequest req) {
        Task task = new Task();
        task.setUserId(userId);
        task.setSemesterId(semesterId);
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setDate(req.getDate());
        task.setColor(req.getColor() != null ? req.getColor() : "#3B82F6");
        task.setChecklist(req.getChecklist());
        task.setCompleted(req.isCompleted());
        return taskRepository.save(task);
    }

    public Task update(String userId, String taskId, TaskRequest req) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setDate(req.getDate());
        if (req.getColor() != null) task.setColor(req.getColor());
        task.setChecklist(req.getChecklist());
        task.setCompleted(req.isCompleted());
        return taskRepository.save(task);
    }

    public void delete(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
    }
}
