package com.taskmanager.usecases.ports;

import com.taskmanager.entities.User;
import com.taskmanager.entities.UserRole;
import com.taskmanager.entities.UserStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for User operations.
 * This is an output port that will be implemented by the infrastructure layer.
 */
public interface UserRepository {
    User save(User user);
    Optional<User> findById(String id);
    Optional<User> findByEmail(String email);
    List<User> findAll();
    /**
     * Returns all users with the given status.
     */
    List<User> findActiveUsers(UserStatus status);
    List<User> findByRole(UserRole role);
    boolean existsById(String id);
    boolean existsByEmail(String email);
    void deleteById(String id);
    /**
     * Returns the count of users with the given status.
     */
    long countActiveUsers(UserStatus status);
    long countByRole(UserRole role);

    /**
     * Returns all users with status ACTIVE.
     */
    default List<User> findActiveUsers() {
        return findActiveUsers(UserStatus.ACTIVE);
    }
    /**
     * Returns the count of users with status ACTIVE.
     */
    default long countActiveUsers() {
        return countActiveUsers(UserStatus.ACTIVE);
    }
}