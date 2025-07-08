package com.taskmanager.adapters.repositories;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.ports.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JpaTaskRepository is the implementation of the TaskRepository output port.
 * It belongs to the 'Frameworks & Drivers' layer in Clean Architecture.
 * This class adapts the domain repository interface to Spring Data JPA.
 */
@Repository
public interface JpaTaskRepository extends JpaRepository<Task, String>, TaskRepository {
    @Query("SELECT t FROM Task t WHERE t.assignedUserId = :userId")
    List<Task> findByUserId(String userId);

    @Query("SELECT t FROM Task t WHERE t.projectId = :projectId")
    List<Task> findByProjectId(String projectId);

    @Query("SELECT t FROM Task t WHERE t.status = :status")
    List<Task> findByStatus(TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.priority = :priority")
    List<Task> findByPriority(TaskPriority priority);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :currentTime AND t.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findOverdueTasks(LocalDateTime currentTime);

    @Query("SELECT t FROM Task t WHERE t.dueDate < :dueDateLimit AND t.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Task> findTasksDueWithin(LocalDateTime dueDateLimit);

    @Query("SELECT t FROM Task t WHERE t.assignedUserId = :userId AND t.status = :status")
    List<Task> findByUserIdAndStatus(String userId, TaskStatus status);

    @Query("SELECT t FROM Task t WHERE t.projectId = :projectId AND t.status = :status")
    List<Task> findByProjectIdAndStatus(String projectId, TaskStatus status);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.assignedUserId = :userId")
    long countByUserId(String userId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.projectId = :projectId")
    long countByProjectId(String projectId);

    @Query("SELECT COUNT(t) FROM Task t WHERE t.status = :status")
    long countByStatus(TaskStatus status);
}