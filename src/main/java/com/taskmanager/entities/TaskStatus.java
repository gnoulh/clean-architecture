package com.taskmanager.entities;

/**
 * Enumeration representing the possible statuses of a task
 */
public enum TaskStatus {
    TODO("To Do"),
    IN_PROGRESS("In Progress"),
    COMPLETED("Completed"),
    CANCELLED("Cancelled");

    private final String displayName;

    TaskStatus(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if the status represents an active task
     */
    public boolean isActive() {
        return this == TODO || this == IN_PROGRESS;
    }

    /**
     * Checks if the status represents a completed task
     */
    public boolean isCompleted() {
        return this == COMPLETED;
    }

    /**
     * Checks if the status represents a cancelled task
     */
    public boolean isCancelled() {
        return this == CANCELLED;
    }
}