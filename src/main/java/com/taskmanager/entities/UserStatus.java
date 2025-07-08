package com.taskmanager.entities;

/**
 * Enumeration representing the possible statuses of a user
 */
public enum UserStatus {
    ACTIVE("Active"),
    INACTIVE("Inactive"),
    DELETED("Deleted");

    private final String displayName;

    UserStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the status represents an active user
     */
    public boolean isActive() {
        return this == ACTIVE;
    }

    /**
     * Checks if the status represents an inactive user
     */
    public boolean isInactive() {
        return this == INACTIVE;
    }

    /**
     * Checks if the status represents a deleted user
     */
    public boolean isDeleted() {
        return this == DELETED;
    }

    /**
     * Checks if the user can perform actions
     */
    public boolean canPerformActions() {
        return this == ACTIVE;
    }
}