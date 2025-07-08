package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTaskStatusUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskStatusUseCase.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public UpdateTaskStatusUseCase(TaskRepository taskRepository, UserRepository userRepository,
                                  ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskOutputData execute(String taskId, TaskStatus newStatus) {
        logger.info("Updating task {} status to {}", taskId, newStatus);

        // Validate task exists
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));

        // Validate project status
        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + task.getProjectId()));
        if (!project.getStatus().canAcceptTasks()) {
            logger.warn("Cannot update task in project status: {}", project.getStatus());
            throw new BusinessRuleViolationException("Cannot update tasks in project status: " + project.getStatus());
        }

        // Update status
        switch (newStatus) {
            case COMPLETED:
                task.markAsCompleted();
                break;
            case IN_PROGRESS:
                task.markAsInProgress();
                break;
            case CANCELLED:
                task.cancel();
                break;
            case TODO:
                task.updateDetails(task.getTitle(), task.getDescription(), task.getDueDate(), task.getPriority());
                break;
            default:
                throw new IllegalArgumentException("Invalid task status: " + newStatus);
        }

        // Save updated task
        Task updatedTask = taskRepository.save(task);

        // Get assigned user
        User assignedUser = userRepository.findById(updatedTask.getAssignedUserId())
                .orElseThrow(() -> new UserNotFoundException("Assigned user not found"));

        logger.info("Task {} status updated to {}", taskId, newStatus);
        return new TaskOutputData(
                updatedTask.getId(),
                updatedTask.getTitle(),
                updatedTask.getDescription(),
                updatedTask.getDueDate(),
                updatedTask.getStatus(),
                updatedTask.getPriority(),
                updatedTask.getAssignedUserId(),
                assignedUser.getFullName(),
                updatedTask.getProjectId(),
                project.getName(),
                updatedTask.getCreatedAt(),
                updatedTask.getUpdatedAt(),
                updatedTask.isOverdue(),
                updatedTask.getDaysUntilDue()
        );
    }
}