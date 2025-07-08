package com.taskmanager.adapters.repositories;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.ports.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * JpaProjectRepository is the implementation of the ProjectRepository output port.
 * It belongs to the 'Frameworks & Drivers' layer in Clean Architecture.
 * This class adapts the domain repository interface to Spring Data JPA.
 */
@Repository
public interface JpaProjectRepository extends JpaRepository<Project, String>, ProjectRepository {
    @Query("SELECT p FROM Project p WHERE p.ownerId = :ownerId")
    List<Project> findByOwnerId(String ownerId);

    @Query("SELECT p FROM Project p WHERE p.status = :status")
    List<Project> findByStatus(ProjectStatus status);

    @Query("SELECT p FROM Project p WHERE p.status IN ('PLANNING', 'IN_PROGRESS')")
    List<Project> findActiveProjects();

    @Query("SELECT p FROM Project p WHERE p.endDate < :currentTime AND p.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Project> findOverdueProjects(@Param("currentTime") LocalDateTime currentTime);

    @Query("SELECT p FROM Project p WHERE p.endDate < :endDateLimit AND p.status NOT IN ('COMPLETED', 'CANCELLED')")
    List<Project> findProjectsEndingWithin(@Param("endDateLimit") LocalDateTime endDateLimit);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.ownerId = :ownerId")
    long countByOwnerId(String ownerId);

    @Query("SELECT COUNT(p) FROM Project p WHERE p.status = :status")
    long countByStatus(ProjectStatus status);
}