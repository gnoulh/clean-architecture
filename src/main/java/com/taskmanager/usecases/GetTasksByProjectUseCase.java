package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import java.util.List;
import java.util.stream.Collectors;

public class GetTasksByProjectUseCase {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public GetTasksByProjectUseCase(TaskRepository taskRepository, UserRepository userRepository,
                                   ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<TaskOutputData> execute(String projectId) {
        // Validate project exists
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + projectId));

        // Get tasks
        List<Task> tasks = taskRepository.findByProjectId(projectId);

        // Map to output data
        return tasks.stream()
                .map(task -> {
                    User assignedUser = userRepository.findById(task.getAssignedUserId())
                            .orElseThrow(() -> new UserNotFoundException("Assigned user not found"));
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
                })
                .collect(Collectors.toList());
    }
}