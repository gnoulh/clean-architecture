package com.taskmanager.entities;

/**
 * Enumeration representing the possible statuses of a project
 */
public enum ProjectStatus {
    PLANNING("Planning"),
    IN_PROGRESS("In Progress"),
    ON_HOLD("On Hold"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    ProjectStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the status represents an active project
     */
    public boolean isActive() {
        return this == PLANNING || this == IN_PROGRESS;
    }

    /**
     * Checks if the status represents a completed project
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * Checks if the status represents a cancelled project
     */
    public boolean isCancelled() {
        return this == CANCELLED;
    }

    /**
     * Checks if the status represents a project on hold
     */
    public boolean isOnHold() {
        return this == ON_HOLD;
    }

    /**
     * Checks if the status represents a project in progress
     */
    public boolean isInProgress() {
        return this == IN_PROGRESS;
    }

    /**
     * Checks if the project can accept new tasks
     */
    public boolean canAcceptTasks() {
        return this == PLANNING || this == IN_PROGRESS;
    }

    /**
     * Checks if the project can be modified
     */
    public boolean canBeModified() {
        return this != COMPLETED && this != CANCELLED;
    }
}