package com.taskmanager.entities;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;
import java.util.regex.Pattern;

/**
 * User entity representing the core business object for user management.
 * Contains business rules and validation logic independent of any framework.
 */
@Entity
@Table(name = "users")
public class User {
    @Id
    @Column(name = "id")
    private String id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "password", nullable = false)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private UserRole role;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private UserStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "last_login_at", nullable = true)
    private LocalDateTime lastLoginAt;

    // Email validation pattern
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");

    // Constructor for creating new users
    public User(String email, String firstName, String lastName, String password, UserRole role) {
        this.id = UUID.randomUUID().toString();
        this.email = validateEmail(email);
        this.firstName = validateName(firstName, "First name");
        this.lastName = validateName(lastName, "Last name");
        this.password = validatePassword(password);
        this.role = role != null ? role : UserRole.USER;
        this.status = UserStatus.ACTIVE;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    // Constructor for loading existing users
    public User(String id, String email, String firstName, String lastName, String password,
                UserRole role, UserStatus status, LocalDateTime createdAt, 
                LocalDateTime updatedAt, LocalDateTime lastLoginAt) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.lastLoginAt = lastLoginAt;
    }

    // No-args constructor for JPA
    public User() {
        // Required by JPA
    }

    // Business Rules

    /**
     * Updates user profile information
     */
    public void updateProfile(String firstName, String lastName, String email) {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalStateException("Cannot update profile of deleted user");
        }
        
        this.firstName = validateName(firstName, "First name");
        this.lastName = validateName(lastName, "Last name");
        this.email = validateEmail(email);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Changes user password
     */
    public void changePassword(String newPassword) {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalStateException("Cannot change password of deleted user");
        }
        
        this.password = validatePassword(newPassword);
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Deactivates the user account
     */
    public void deactivate() {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalStateException("Cannot deactivate deleted user");
        }
        
        this.status = UserStatus.INACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Activates the user account
     */
    public void activate() {
        if (this.status == UserStatus.DELETED) {
            throw new IllegalStateException("Cannot activate deleted user");
        }
        
        this.status = UserStatus.ACTIVE;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Marks user as deleted (soft delete)
     */
    public void markAsDeleted() {
        this.status = UserStatus.DELETED;
        this.updatedAt = LocalDateTime.now();
    }

    /**
     * Updates last login timestamp
     */
    public void updateLastLogin() {
        if (this.status != UserStatus.ACTIVE) {
            throw new IllegalStateException("Only active users can login");
        }
        
        this.lastLoginAt = LocalDateTime.now();
    }

    /**
     * Checks if user can be assigned tasks
     */
    public boolean canBeAssignedTasks() {
        return this.status == UserStatus.ACTIVE;
    }

    /**
     * Checks if user has administrative privileges
     */
    public boolean isAdmin() {
        return this.role == UserRole.ADMIN;
    }

    /**
     * Checks if user is a project manager
     */
    public boolean isProjectManager() {
        return this.role == UserRole.PROJECT_MANAGER || this.role == UserRole.ADMIN;
    }

    /**
     * Gets user's full name
     */
    public String getFullName() {
        return firstName + " " + lastName;
    }

    /**
     * Gets user's display name (first name + last initial)
     */
    public String getDisplayName() {
        return firstName + " " + (lastName != null && !lastName.isEmpty() ? lastName.charAt(0) + "." : "");
    }

    /**
     * Checks if user has been active recently (within last 30 days)
     */
    public boolean isRecentlyActive() {
        return lastLoginAt != null && 
               lastLoginAt.isAfter(LocalDateTime.now().minusDays(30));
    }

    // Validation methods
    private String validateEmail(String email) {
        if (email == null || email.trim().isEmpty()) {
            throw new IllegalArgumentException("Email cannot be null or empty");
        }
        
        email = email.trim().toLowerCase();
        if (!EMAIL_PATTERN.matcher(email).matches()) {
            throw new IllegalArgumentException("Invalid email format");
        }
        
        return email;
    }

    private String validateName(String name, String fieldName) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException(fieldName + " cannot be null or empty");
        }
        if (name.length() > 50) {
            throw new IllegalArgumentException(fieldName + " cannot exceed 50 characters");
        }
        return name.trim();
    }    private String validatePassword(String password) {
        if (password == null || password.length() < 8) {
            throw new IllegalArgumentException("Password must be at least 8 characters long");
        }
        if (password.length() > 100) {
            throw new IllegalArgumentException("Password cannot exceed 100 characters");
        }
        if (!password.matches(".*[A-Z].*") || !password.matches(".*[a-z].*") || 
            !password.matches(".*\\d.*") || !password.matches(".*[!@#$%^&*()].*")) {
            throw new IllegalArgumentException("Password must contain at least one uppercase letter, " +
                "one lowercase letter, one number, and one special character");
        }
        // TODO: Use PasswordEncoder bean for hashing in service layer
        return password;
    }

    // Getters
    public String getId() { return id; }
    public String getEmail() { return email; }
    public String getFirstName() { return firstName; }
    public String getLastName() { return lastName; }
    public String getPassword() { return password; }
    public UserRole getRole() { return role; }
    public UserStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public LocalDateTime getLastLoginAt() { return lastLoginAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "User{" +
                "id='" + id + '\'' +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", role=" + role +
                ", status=" + status +
                '}';
    }
}