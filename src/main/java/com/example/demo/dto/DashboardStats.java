package com.example.demo.dto;
import lombok.Builder;
import lombok.Data;
import java.util.List;
import java.util.Map;

@Data @Builder
public class DashboardStats {
    private int totalTasks;
    private int completedTasks;
    private int pendingTasks;
    private int overdueTasks;
    private int totalSemesters;
    private int totalNotes;
    private double completionRate;        // 0-100
    private Map<String, Integer> tasksByPriority;
    private Map<String, Integer> tasksBySubject;
    private List<UpcomingTask> upcomingTasks;  // next 7 days
}
