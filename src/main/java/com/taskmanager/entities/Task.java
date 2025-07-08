package com.taskmanager.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Task entity representing the core business object for task management.
 * Contains business rules and validation logic independent of any framework.
 */
@Entity
@Table(name = "tasks")
public class Task {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "due_date")
    private LocalDateTime dueDate;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private TaskStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "priority", nullable = false)
    private TaskPriority priority;

    @Column(name = "assigned_user_id", nullable = false)
    private String assignedUserId;

    @Column(name = "project_id", nullable = false)
    private String projectId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructor for creating new tasks
    public Task(String title, String description, LocalDateTime dueDate, 
                String assignedUserId, String projectId, TaskPriority priority) {
        this.id = UUID.randomUUID().toString();
        this.title = validateTitle(title);
        this.description = description;
        this.dueDate = dueDate;
        this.assignedUserId = assignedUserId;
        this.projectId = projectId;
        this.priority = priority != null ? priority : TaskPriority.MEDIUM;
        this.status = TaskStatus.TODO;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor for loading existing tasks
    public Task(String id, String title, String description, LocalDateTime dueDate,
                TaskStatus status, TaskPriority priority, String assignedUserId, 
                String projectId, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.dueDate = dueDate;
        this.status = status;
        this.priority = priority;
        this.assignedUserId = assignedUserId;
        this.projectId = projectId;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // No-args constructor for JPA
    public Task() {
        // Required by JPA
    }

    // Business Rules

    /**
     * Marks task as completed if it's in a valid state to be completed
     */
    public void markAsCompleted() {
        if (this.status == TaskStatus.CANCELLED) {
            throw new IllegalStateException("Cannot complete a cancelled task");
        }
        this.status = TaskStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks task as in progress if it's in a valid state
     */
    public void markAsInProgress() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Cannot mark a completed task as in progress");
        }
        if (this.status == TaskStatus.CANCELLED) {
            throw new IllegalStateException("Cannot mark a cancelled task as in progress");
        }
        this.status = TaskStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancels the task if it's not already completed
     */
    public void cancel() {
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed task");
        }
        this.status = TaskStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks if the task is overdue
     */
    public boolean isOverdue() {
        return dueDate != null && 
               LocalDateTime.now().isAfter(dueDate) && 
               status != TaskStatus.COMPLETED && 
               status != TaskStatus.CANCELLED;
    }

    /**
     * Updates task details
     */
    public void updateDetails(String title, String description, LocalDateTime dueDate, 
                             TaskPriority priority) {
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Cannot update a completed task");
        }
        if (this.status == TaskStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update a cancelled task");
        }
        
        this.title = validateTitle(title);
        this.description = description;
        this.dueDate = dueDate;
        this.priority = priority != null ? priority : this.priority;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Assigns task to a different user
     */
    public void assignTo(String userId) {
        if (this.status == TaskStatus.COMPLETED) {
            throw new IllegalStateException("Cannot reassign a completed task");
        }
        if (this.status == TaskStatus.CANCELLED) {
            throw new IllegalStateException("Cannot reassign a cancelled task");
        }
        
        this.assignedUserId = userId;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Calculates the number of days until due date
     */
    public long getDaysUntilDue() {
        if (dueDate == null) {
            return Long.MAX_VALUE;
        }
        return LocalDateTime.now().until(dueDate, java.time.temporal.ChronoUnit.DAYS);
    }

    /**
     * Checks if task is high priority and overdue
     */
    public boolean isUrgent() {
        return priority == TaskPriority.HIGH && isOverdue();
    }

    // Validation methods
    private String validateTitle(String title) {
        if (title == null || title.trim().isEmpty()) {
            throw new IllegalArgumentException("Task title cannot be null or empty");
        }
        if (title.length() > 200) {
            throw new IllegalArgumentException("Task title cannot exceed 200 characters");
        }
        return title.trim();
    }

    // Getters
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public LocalDateTime getDueDate() { return dueDate; }
    public TaskStatus getStatus() { return status; }
    public TaskPriority getPriority() { return priority; }
    public String getAssignedUserId() { return assignedUserId; }
    public String getProjectId() { return projectId; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(id, task.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", priority=" + priority +
                ", dueDate=" + dueDate +
                '}';
    }
}