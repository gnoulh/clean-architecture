package com.taskmanager.usecases.dto;

import com.taskmanager.entities.UserRole;
import java.time.LocalDateTime;

public class UserOutputData {
    private String id;
    private String email;
    private String firstName;
    private String lastName;
    private UserRole role;
    private String fullName;
    private String displayName;
    private LocalDateTime createdAt;
    private LocalDateTime lastLoginAt;
    private boolean isRecentlyActive;

    public UserOutputData(String id, String email, String firstName, String lastName,
                          UserRole role, String fullName, String displayName,
                          LocalDateTime createdAt, LocalDateTime lastLoginAt,
                          boolean isRecentlyActive) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.role = role;
        this.fullName = fullName;
        this.displayName = displayName;
        this.createdAt = createdAt;
        this.lastLoginAt = lastLoginAt;
        this.isRecentlyActive = isRecentlyActive;
    }

    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public UserRole getRole() { return role; }
    public String getFullName() { return fullName; }
    public String getDisplayName() { return displayName; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }
    public boolean isRecentlyActive() { return isRecentlyActive; }
}