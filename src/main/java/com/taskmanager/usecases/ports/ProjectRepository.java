package com.taskmanager.usecases.ports;

import com.taskmanager.entities.Project;
import com.taskmanager.entities.ProjectStatus;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Project operations.
 * This is an output port that will be implemented by the infrastructure layer.
 */
public interface ProjectRepository {
    Project save(Project project);
    Optional<Project> findById(String id);
    List<Project> findAll();
    List<Project> findByOwnerId(String ownerId);
    List<Project> findByStatus(com.taskmanager.entities.ProjectStatus status);
    List<Project> findActiveProjects();
    /**
     * Finds overdue projects as of the given time.
     */
    List<Project> findOverdueProjects(LocalDateTime currentTime);
    /**
     * Finds projects ending before the given date and not completed or cancelled.
     */
    List<Project> findProjectsEndingWithin(LocalDateTime endDateLimit);
    boolean existsById(String id);
    void deleteById(String id);
    long countByOwnerId(String ownerId);
    long countByStatus(com.taskmanager.entities.ProjectStatus status);
}