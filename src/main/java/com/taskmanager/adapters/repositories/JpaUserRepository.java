package com.taskmanager.adapters.repositories;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.ports.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * JpaUserRepository is the implementation of the UserRepository output port.
 * It belongs to the 'Frameworks & Drivers' layer in Clean Architecture.
 * This class adapts the domain repository interface to Spring Data JPA.
 */
@Repository
public interface JpaUserRepository extends JpaRepository<User, String>, UserRepository {
    @Query("SELECT u FROM User u WHERE u.email = :email")
    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.status = :status")
    List<User> findActiveUsers(UserStatus status);

    @Query("SELECT u FROM User u WHERE u.role = :role")
    List<User> findByRole(UserRole role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.status = :status")
    long countActiveUsers(UserStatus status);

    @Query("SELECT COUNT(u) FROM User u WHERE u.role = :role")
    long countByRole(UserRole role);

    @Query("SELECT COUNT(u) FROM User u WHERE u.email = :email")
    long countByEmail(String email);

    default boolean existsByEmail(String email) {
        return countByEmail(email) > 0;
    }
}