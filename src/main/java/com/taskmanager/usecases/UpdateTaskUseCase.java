package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class UpdateTaskUseCase {
    private static final Logger logger = LoggerFactory.getLogger(UpdateTaskUseCase.class);
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public UpdateTaskUseCase(TaskRepository taskRepository, UserRepository userRepository,
                            ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskOutputData execute(UpdateTaskInputData inputData) {
        logger.info("Updating task: {}", inputData.getTaskId());

        // Validate task exists
        Task task = taskRepository.findById(inputData.getTaskId())
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + inputData.getTaskId()));

        // Validate project exists and can accept tasks
        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + task.getProjectId()));
        
        if (!project.getStatus().canAcceptTasks()) {
            logger.warn("Cannot update tasks in project status: {}", project.getStatus());
            throw new BusinessRuleViolationException("Cannot update tasks in project status: " + project.getStatus());
        }

        // Update task details
        task.updateDetails(
                inputData.getTitle(),
                inputData.getDescription(),
                inputData.getDueDate(),
                inputData.getPriority()
        );

        // Save updated task
        Task updatedTask = taskRepository.save(task);

        // Get assigned user for output data
        User assignedUser = userRepository.findById(updatedTask.getAssignedUserId())
                .orElseThrow(() -> new UserNotFoundException("Assigned user not found"));

        logger.info("Task updated successfully: {}", updatedTask.getId());
        return createTaskOutputData(updatedTask, assignedUser, project);
    }

    private TaskOutputData createTaskOutputData(Task task, User assignedUser, Project project) {
        return new TaskOutputData(
                task.getId(),
                task.getTitle(),
                task.getDescription(),
                task.getDueDate(),
                task.getStatus(),
                task.getPriority(),
                task.getAssignedUserId(),
                assignedUser.getFullName(),
                task.getProjectId(),
                project.getName(),
                task.getCreatedAt(),
                task.getUpdatedAt(),
                task.isOverdue(),
                task.getDaysUntilDue()
        );
    }
}