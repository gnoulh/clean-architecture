package com.taskmanager.entities;

/**
 * Enumeration representing the possible roles of a user
 */
public enum UserRole {
    USER("User"),
    PROJECT_MANAGER("Project Manager"),
    ADMIN("Administrator");

    private final String displayName;

    UserRole(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }

    /**
     * Checks if this role has administrative privileges
     */
    public boolean hasAdminPrivileges() {
        return this == ADMIN;
    }

    /**
     * Checks if this role can manage projects
     */
    public boolean canManageProjects() {
        return this == PROJECT_MANAGER || this == ADMIN;
    }

    /**
     * Checks if this role can manage users
     */
    public boolean canManageUsers() {
        return this == ADMIN;
    }

    /**
     * Checks if this role has higher privileges than the given role
     */
    public boolean hasHigherPrivilegesThan(UserRole other) {
        return this.ordinal() > other.ordinal();
    }
}