package com.taskmanager.usecases.dto;

import com.taskmanager.entities.TaskPriority;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;

public class CreateTaskInputData {
    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    @NotBlank(message = "Assigned user ID is required")
    private String assignedUserId;

    @NotBlank(message = "Project ID is required")
    private String projectId;

    @NotNull(message = "Priority is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TaskPriority priority;

    public CreateTaskInputData(String title, String description, LocalDateTime dueDate,
                               String assignedUserId, String projectId, TaskPriority priority) {
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.assignedUserId = assignedUserId;
        this.projectId = projectId;
        this.priority = priority;
    }

    public CreateTaskInputData() {}

    // Getters
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public String getAssignedUserId() { return assignedUserId; }
    public String getProjectId() { return projectId; }
    public TaskPriority getPriority() { return priority; }
}