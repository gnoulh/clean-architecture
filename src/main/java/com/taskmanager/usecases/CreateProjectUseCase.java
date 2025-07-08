package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateProjectUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateProjectUseCase.class);
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public CreateProjectUseCase(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    public ProjectOutputData execute(CreateProjectInputData inputData) {
        logger.info("Creating project: {}", inputData.getName());

        // Validate owner exists and has appropriate role
        User owner = userRepository.findById(inputData.getOwnerId())
                .orElseThrow(() -> new UserNotFoundException("Owner not found: " + inputData.getOwnerId()));
        if (!owner.isProjectManager()) {
            logger.warn("User {} is not authorized to create projects", inputData.getOwnerId());
            throw new UnauthorizedAccessException("User is not authorized to create projects");
        }

        // Create project entity
        Project project = new Project(
                inputData.getName(),
                inputData.getDescription(),
                inputData.getOwnerId(),
                inputData.getStartDate(),
                inputData.getEndDate()
        );

        // Save project
        Project savedProject = projectRepository.save(project);
        logger.info("Project created successfully: {}", savedProject.getId());

        // Return output data
        return new ProjectOutputData(
                savedProject.getId(),
                savedProject.getName(),
                savedProject.getDescription(),
                savedProject.getOwnerId(),
                owner.getFullName(),
                savedProject.getStatus(),
                savedProject.getStartDate(),
                savedProject.getEndDate(),
                savedProject.getCreatedAt(),
                savedProject.isOverdue(),
                savedProject.getDaysRemaining(),
                savedProject.getTimeProgress(),
                savedProject.isNearDeadline()
        );
    }
}