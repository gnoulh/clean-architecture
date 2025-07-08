package com.taskmanager.usecases.dto;

import com.taskmanager.entities.ProjectStatus;
import java.time.LocalDateTime;

public class ProjectOutputData {
    private String id;
    private String name;
    private String description;
    private String ownerId;
    private String ownerName;
    private ProjectStatus status;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private LocalDateTime createdAt;
    private boolean isOverdue;
    private long daysRemaining;
    private double timeProgress;
    private boolean isNearDeadline;

    public ProjectOutputData(String id, String name, String description, String ownerId,
                            String ownerName, ProjectStatus status, LocalDateTime startDate,
                            LocalDateTime endDate, LocalDateTime createdAt, boolean isOverdue,
                            long daysRemaining, double timeProgress, boolean isNearDeadline) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.ownerName = ownerName;
        this.status = status;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createdAt = createdAt;
        this.isOverdue = isOverdue;
        this.daysRemaining = daysRemaining;
        this.timeProgress = timeProgress;
        this.isNearDeadline = isNearDeadline;
    }

    // Getters
    public String getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOwnerId() { return ownerId; }
    public String getOwnerName() { return ownerName; }
    public ProjectStatus getStatus() { return status; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public boolean isOverdue() { return isOverdue; }
    public long getDaysRemaining() { return daysRemaining; }
    public double getTimeProgress() { return timeProgress; }
    public boolean isNearDeadline() { return isNearDeadline; }
}