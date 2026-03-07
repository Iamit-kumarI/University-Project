package com.example.demo.service;
import com.example.demo.dto.TaskRequest;
import com.example.demo.model.Task;
import com.example.demo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> getByMonth(String userId, String semesterId, int year, int month) {
        return taskRepository.findByUserIdAndSemesterId(userId, semesterId).stream()
                .filter(t -> t.getDate() != null
                        && t.getDate().getYear() == year
                        && t.getDate().getMonthValue() == month)
                .toList();
    }

    public List<Task> getByDate(String userId, String semesterId, LocalDate date) {
        return taskRepository.findByUserIdAndSemesterIdAndDate(userId, semesterId, date);
    }

    public List<Task> getAllForSemester(String userId, String semesterId) {
        return taskRepository.findByUserIdAndSemesterId(userId, semesterId);
    }

    public Task create(String userId, String semesterId, TaskRequest req) {
        Task task = new Task();
        task.setUserId(userId);
        task.setSemesterId(semesterId);
        applyRequest(task, req);
        task.setCreatedAt(LocalDateTime.now());
        if (req.isCompleted()) task.setCompletedAt(LocalDateTime.now());
        return taskRepository.save(task);
    }

    public Task update(String userId, String taskId, TaskRequest req) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        boolean wasCompleted = task.isCompleted();
        applyRequest(task, req);
        if (!wasCompleted && req.isCompleted()) task.setCompletedAt(LocalDateTime.now());
        if (wasCompleted && !req.isCompleted()) task.setCompletedAt(null);
        return taskRepository.save(task);
    }

    // PATCH — toggle completed only
    public Task toggleComplete(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        task.setCompleted(!task.isCompleted());
        task.setCompletedAt(task.isCompleted() ? LocalDateTime.now() : null);
        return taskRepository.save(task);
    }

    public void delete(String userId, String taskId) {
        Task task = taskRepository.findById(taskId)
                .filter(t -> t.getUserId().equals(userId))
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Task not found"));
        taskRepository.delete(task);
    }

    private void applyRequest(Task task, TaskRequest req) {
        task.setTitle(req.getTitle());
        task.setDescription(req.getDescription());
        task.setDate(req.getDate());
        task.setColor(req.getColor() != null ? req.getColor() : "#6366f1");
        task.setCompleted(req.isCompleted());
        task.setPriority(req.getPriority() != null ? req.getPriority() : "MEDIUM");
        task.setTags(req.getTags());
        task.setChecklist(req.getChecklist());
        task.setDueTime(req.getDueTime());
        task.setSubject(req.getSubject());
    }
}
