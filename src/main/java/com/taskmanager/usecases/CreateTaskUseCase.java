package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Use case for creating a new task
 */
public class CreateTaskUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateTaskUseCase.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public CreateTaskUseCase(TaskRepository taskRepository, UserRepository userRepository,
                                ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskOutputData execute(CreateTaskInputData inputData) {
        logger.info("Creating task with title: {}", inputData.getTitle());

        try {
            // Validate user exists
            User assignedUser = userRepository.findById(inputData.getAssignedUserId())
                    .orElseThrow(() -> {
                        logger.error("User not found: {}", inputData.getAssignedUserId());
                        return new UserNotFoundException("User not found: " + inputData.getAssignedUserId());
                    });

            // Validate project exists and can accept tasks
            Project project = projectRepository.findById(inputData.getProjectId())
                    .orElseThrow(() -> {
                        logger.error("Project not found: {}", inputData.getProjectId());
                        return new ProjectNotFoundException("Project not found: " + inputData.getProjectId());
                    });

            if (!project.getStatus().canAcceptTasks()) {
                logger.warn("Cannot create tasks in project status: {}", project.getStatus());
                throw new BusinessRuleViolationException("Cannot create tasks in project status: " + project.getStatus());
            }

            // Create task
            Task task = new Task(
                inputData.getTitle(),
                inputData.getDescription(),
                inputData.getDueDate(),
                inputData.getAssignedUserId(),
                inputData.getProjectId(),
                inputData.getPriority()
            );

            // Save task
            Task savedTask = taskRepository.save(task);
            logger.info("Task created successfully: {}", savedTask.getId());

            return new TaskOutputData(
                    savedTask.getId(),
                    savedTask.getTitle(),
                    savedTask.getDescription(),
                    savedTask.getDueDate(),
                    savedTask.getStatus(),
                    savedTask.getPriority(),
                    savedTask.getAssignedUserId(),
                    assignedUser.getFullName(),
                    savedTask.getProjectId(),
                    project.getName(),
                    savedTask.getCreatedAt(),
                    savedTask.getUpdatedAt(),
                    savedTask.isOverdue(),
                    savedTask.getDaysUntilDue()
            );
        } catch (Exception e) {
            logger.error("Failed to create task: {}", e.getMessage(), e);
            throw e;
        }
    }
}