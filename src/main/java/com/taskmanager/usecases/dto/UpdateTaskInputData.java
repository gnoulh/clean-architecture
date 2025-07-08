package com.taskmanager.usecases.dto;

import com.taskmanager.entities.TaskPriority;
import java.time.LocalDateTime;
import jakarta.validation.constraints.*;
import com.fasterxml.jackson.annotation.JsonFormat;

public class UpdateTaskInputData {
    @NotBlank(message = "Task ID is required")
    private String taskId;

    @NotBlank(message = "Title is required")
    @Size(max = 200, message = "Title cannot exceed 200 characters")
    private String title;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @FutureOrPresent(message = "Due date must be in the present or future")
    private LocalDateTime dueDate;

    @NotNull(message = "Priority is required")
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TaskPriority priority;

    public UpdateTaskInputData(String taskId, String title, String description,
                               LocalDateTime dueDate, TaskPriority priority) {
        this.taskId = taskId;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority;
    }

    // Public no-args constructor
    public UpdateTaskInputData() {}

    // Getters
    public String getTaskId() { return taskId; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public TaskPriority getPriority() { return priority; }
}