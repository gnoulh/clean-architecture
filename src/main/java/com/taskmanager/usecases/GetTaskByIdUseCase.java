package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;

public class GetTaskByIdUseCase {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public GetTaskByIdUseCase(TaskRepository taskRepository, UserRepository userRepository,
                             ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public TaskOutputData execute(String taskId) {
        // Find task
        Task task = taskRepository.findById(taskId)
                .orElseThrow(() -> new TaskNotFoundException("Task not found: " + taskId));

        // Get assigned user
        User assignedUser = userRepository.findById(task.getAssignedUserId())
                .orElseThrow(() -> new UserNotFoundException("Assigned user not found"));

        // Get project
        Project project = projectRepository.findById(task.getProjectId())
                .orElseThrow(() -> new ProjectNotFoundException("Project not found"));

        // Return output data
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