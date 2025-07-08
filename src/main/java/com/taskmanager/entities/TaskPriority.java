package com.taskmanager.entities;

/**
 * Enumeration representing the priority levels of a task
 */
public enum TaskPriority {
    LOW(1, "Low"),
    MEDIUM(2, "Medium"),
    HIGH(3, "High"),
    URGENT(4, "Urgent");

    private final int value;
    private final String displayName;

    TaskPriority(int value, String displayName) {
        this.value = value;
        this.displayName = displayName;
    }

    public int getValue() {
        return value;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if this priority is higher than the given priority
     */
    public boolean isHigherThan(TaskPriority other) {
        return this.value > other.value;
    }

    /**
     * Checks if this priority is lower than the given priority
     */
    public boolean isLowerThan(TaskPriority other) {
        return this.value < other.value;
    }

    /**
     * Gets priority from integer value
     */
    public static TaskPriority fromValue(int value) {
        for (TaskPriority priority : values()) {
            if (priority.value == value) {
                return priority;
            }
        }
        throw new IllegalArgumentException("Invalid priority value: " + value);
    }
}