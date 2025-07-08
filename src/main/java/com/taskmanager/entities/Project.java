package com.taskmanager.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Project entity representing the core business object for project management.
 * Contains business rules and validation logic independent of any framework.
 */
@Entity
@Table(name = "projects")
public class Project {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "owner_id", nullable = false)
    private String ownerId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ProjectStatus status;

    @Column(name = "start_date")
    private LocalDateTime startDate;

    @Column(name = "end_date")
    private LocalDateTime endDate;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    // Constructor for creating new projects
    public Project(String name, String description, String ownerId, 
                   LocalDateTime startDate, LocalDateTime endDate) {
        this.id = UUID.randomUUID().toString();
        this.name = validateName(name);
        this.description = description;
        this.ownerId = validateOwnerId(ownerId);
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = ProjectStatus.PLANNING;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        
        validateDateRange();
    }

    // Constructor for loading existing projects
    public Project(String id, String name, String description, String ownerId,
                   ProjectStatus status, LocalDateTime startDate, LocalDateTime endDate,
                   LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // No-args constructor for JPA
    public Project() {
        // Required by JPA
    }

    // Business Rules

    /**
     * Starts the project if it's in planning status
     */
    public void start() {
        if (this.status != ProjectStatus.PLANNING) {
            throw new IllegalStateException("Can only start projects in planning status");
        }
        
        this.status = ProjectStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Completes the project if it's in progress
     */
    public void complete() {
        if (this.status != ProjectStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only complete projects that are in progress");
        }
        
        this.status = ProjectStatus.COMPLETED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Cancels the project if it's not already completed
     */
    public void cancel() {
        if (this.status == ProjectStatus.COMPLETED) {
            throw new IllegalStateException("Cannot cancel a completed project");
        }
        
        this.status = ProjectStatus.CANCELLED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Puts the project on hold
     */
    public void putOnHold() {
        if (this.status != ProjectStatus.IN_PROGRESS) {
            throw new IllegalStateException("Can only put in-progress projects on hold");
        }
        
        this.status = ProjectStatus.ON_HOLD;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Resumes the project from hold
     */
    public void resume() {
        if (this.status != ProjectStatus.ON_HOLD) {
            throw new IllegalStateException("Can only resume projects that are on hold");
        }
        
        this.status = ProjectStatus.IN_PROGRESS;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates project details
     */
    public void updateDetails(String name, String description, 
                             LocalDateTime startDate, LocalDateTime endDate) {
        if (this.status == ProjectStatus.COMPLETED) {
            throw new IllegalStateException("Cannot update completed project");
        }
        if (this.status == ProjectStatus.CANCELLED) {
            throw new IllegalStateException("Cannot update cancelled project");
        }
        
        this.name = validateName(name);
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.updatedAt = LocalDateTime.now();
        
        validateDateRange();
    }

    /**
     * Changes project owner
     */
    public void changeOwner(String newOwnerId) {
        if (this.status == ProjectStatus.COMPLETED) {
            throw new IllegalStateException("Cannot change owner of completed project");
        }
        if (this.status == ProjectStatus.CANCELLED) {
            throw new IllegalStateException("Cannot change owner of cancelled project");
        }
        
        this.ownerId = validateOwnerId(newOwnerId);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Checks if the project is overdue
     */
    public boolean isOverdue() {
        return endDate != null && 
               LocalDateTime.now().isAfter(endDate) && 
               status != ProjectStatus.COMPLETED && 
               status != ProjectStatus.CANCELLED;
    }

    /**
     * Checks if the project is active
     */
    public boolean isActive() {
        return status == ProjectStatus.IN_PROGRESS || status == ProjectStatus.PLANNING;
    }

    /**
     * Calculates project duration in days
     */
    public long getDurationInDays() {
        if (startDate == null || endDate == null) {
            return 0;
        }
        return startDate.until(endDate, java.time.temporal.ChronoUnit.DAYS);
    }

    /**
     * Calculates days remaining until project end
     */
    public long getDaysRemaining() {
        if (endDate == null) {
            return Long.MAX_VALUE;
        }
        return LocalDateTime.now().until(endDate, java.time.temporal.ChronoUnit.DAYS);
    }

    /**
     * Calculates project progress as percentage (0-100)
     * Based on time elapsed vs total duration
     */
    public double getTimeProgress() {
        if (startDate == null || endDate == null) {
            return 0.0;
        }
        
        LocalDateTime now = LocalDateTime.now();
        if (now.isBefore(startDate)) {
            return 0.0;
        }
        if (now.isAfter(endDate)) {
            return 100.0;
        }
        
        long totalDuration = startDate.until(endDate, java.time.temporal.ChronoUnit.DAYS);
        long elapsed = startDate.until(now, java.time.temporal.ChronoUnit.DAYS);
        
        return totalDuration > 0 ? (elapsed * 100.0) / totalDuration : 0.0;
    }

    /**
     * Checks if project is near deadline (within 7 days)
     */
    public boolean isNearDeadline() {
        return getDaysRemaining() <= 7 && getDaysRemaining() > 0;
    }

    // Validation methods
    private String validateName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Project name cannot be null or empty");
        }
        if (name.length() > 100) {
            throw new IllegalArgumentException("Project name cannot exceed 100 characters");
        }
        return name.trim();
    }

    private String validateOwnerId(String ownerId) {
        if (ownerId == null || ownerId.trim().isEmpty()) {
            throw new IllegalArgumentException("Project owner ID cannot be null or empty");
        }
        return ownerId.trim();
    }

    private void validateDateRange() {
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOwnerId() { return ownerId; }
    public ProjectStatus getStatus() { return status; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Project project = (Project) o;
        return Objects.equals(id, project.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Project{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", status=" + status +
                ", ownerId='" + ownerId + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}