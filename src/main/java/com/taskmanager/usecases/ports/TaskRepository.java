package com.taskmanager.usecases.ports;

import com.taskmanager.entities.Task;
import com.taskmanager.entities.TaskStatus;
import com.taskmanager.entities.TaskPriority;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Task operations.
 * This is an output port that will be implemented by the infrastructure layer.
 */
public interface TaskRepository {
    Task save(Task task);
    Optional<Task> findById(String id);
    List<Task> findAll();
    List<Task> findByUserId(String userId);
    List<Task> findByProjectId(String projectId);
    List<Task> findByStatus(TaskStatus status);
    List<Task> findByPriority(TaskPriority priority);
    List<Task> findOverdueTasks(LocalDateTime currentTime);
    List<Task> findTasksDueWithin(LocalDateTime dueDateLimit);
    List<Task> findByUserIdAndStatus(String userId, TaskStatus status);
    List<Task> findByProjectIdAndStatus(String projectId, TaskStatus status);
    boolean existsById(String id);
    void deleteById(String id);
    long countByUserId(String userId);
    long countByProjectId(String projectId);
    long countByStatus(TaskStatus status);
}