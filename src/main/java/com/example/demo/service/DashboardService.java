package com.example.demo.service;
import com.example.demo.dto.*;
import com.example.demo.model.Task;
import com.example.demo.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DashboardService {
    private final TaskRepository taskRepository;
    private final SemesterRepository semesterRepository;
    private final NoteRepository noteRepository;

    public DashboardStats getStats(String userId) {
        List<Task> allTasks = taskRepository.findByUserId(userId);
        LocalDate today = LocalDate.now();
        LocalDate in7 = today.plusDays(7);

        long total     = allTasks.size();
        long completed = allTasks.stream().filter(Task::isCompleted).count();
        long pending   = total - completed;
        long overdue   = allTasks.stream()
                .filter(t -> !t.isCompleted() && t.getDate() != null && t.getDate().isBefore(today))
                .count();

        // Tasks by priority
        Map<String, Integer> byPriority = allTasks.stream()
                .collect(Collectors.groupingBy(
                        t -> t.getPriority() != null ? t.getPriority() : "MEDIUM",
                        Collectors.summingInt(t -> 1)
                ));

        // Tasks by subject
        Map<String, Integer> bySubject = allTasks.stream()
                .filter(t -> t.getSubject() != null && !t.getSubject().isBlank())
                .collect(Collectors.groupingBy(Task::getSubject, Collectors.summingInt(t -> 1)));

        // Upcoming tasks (next 7 days, not completed)
        Map<String, String> semesterNames = new HashMap<>();
        semesterRepository.findByUserId(userId)
                .forEach(s -> semesterNames.put(s.getId(), s.getName()));

        List<UpcomingTask> upcoming = allTasks.stream()
                .filter(t -> !t.isCompleted() && t.getDate() != null
                        && !t.getDate().isBefore(today) && !t.getDate().isAfter(in7))
                .sorted(Comparator.comparing(Task::getDate))
                .limit(10)
                .map(t -> new UpcomingTask(t.getId(), t.getTitle(), t.getSubject(),
                        t.getPriority(), t.getColor(), t.getDate(), t.getDueTime(),
                        semesterNames.getOrDefault(t.getSemesterId(), "")))
                .collect(Collectors.toList());

        double rate = total == 0 ? 0.0 : Math.round((double) completed / total * 1000) / 10.0;

        return DashboardStats.builder()
                .totalTasks((int) total)
                .completedTasks((int) completed)
                .pendingTasks((int) pending)
                .overdueTasks((int) overdue)
                .totalSemesters((int) semesterRepository.countByUserId(userId))
                .totalNotes((int) noteRepository.countByUserId(userId))
                .completionRate(rate)
                .tasksByPriority(byPriority)
                .tasksBySubject(bySubject)
                .upcomingTasks(upcoming)
                .build();
    }
}
