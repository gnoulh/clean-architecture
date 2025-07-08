package com.taskmanager.usecases.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.taskmanager.entities.TaskPriority;
import com.taskmanager.entities.TaskStatus;
import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class TaskOutputData {
    private String id;
    private String title;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime dueDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TaskStatus status;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private TaskPriority priority;
    private String assignedUserId;
    private String assignedUserName;
    private String projectId;
    private String projectName;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime createdAt;
    @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime updatedAt;
    private boolean isOverdue;
    private long daysUntilDue;

    @JsonCreator
    public TaskOutputData(
            @JsonProperty("id") String id,
            @JsonProperty("title") String title,
            @JsonProperty("description") String description,
            @JsonProperty("dueDate") LocalDateTime dueDate,
            @JsonProperty("status") TaskStatus status,
            @JsonProperty("priority") TaskPriority priority,
            @JsonProperty("assignedUserId") String assignedUserId,
            @JsonProperty("assignedUserName") String assignedUserName,
            @JsonProperty("projectId") String projectId,
            @JsonProperty("projectName") String projectName,
            @JsonProperty("createdAt") LocalDateTime createdAt,
            @JsonProperty("updatedAt") LocalDateTime updatedAt,
            @JsonProperty("isOverdue") boolean isOverdue,
            @JsonProperty("daysUntilDue") long daysUntilDue) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.assignedUserId = assignedUserId;
        this.assignedUserName = assignedUserName;
        this.projectId = projectId;
        this.projectName = projectName;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.isOverdue = isOverdue;
        this.daysUntilDue = daysUntilDue;
    }

    // Default constructor for Jackson
    public TaskOutputData() {}

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public String getAssignedUserId() { return assignedUserId; }
    public String getAssignedUserName() { return assignedUserName; }
    public String getProjectId() { return projectId; }
    public String getProjectName() { return projectName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public boolean isOverdue() { return isOverdue; }
    public long getDaysUntilDue() { return daysUntilDue; }
}