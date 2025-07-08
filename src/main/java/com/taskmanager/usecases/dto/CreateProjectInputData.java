package com.taskmanager.usecases.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.*;

public class CreateProjectInputData {
    @NotBlank(message = "Project name is required")
    @Size(max = 100, message = "Project name cannot exceed 100 characters")
    private String name;

    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotBlank(message = "Owner ID is required")
    private String ownerId;

    @NotNull(message = "Start date is required")
    private LocalDateTime startDate;

    @NotNull(message = "End date is required")
    private LocalDateTime endDate;

    public CreateProjectInputData(String name, String description, String ownerId,
                                  LocalDateTime startDate, LocalDateTime endDate) {
        this.name = name;
        this.description = description;
        this.ownerId = ownerId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    // Getters
    public String getName() { return name; }
    public String getDescription() { return description; }
    public String getOwnerId() { return ownerId; }
    public LocalDateTime getStartDate() { return startDate; }
    public LocalDateTime getEndDate() { return endDate; }
}