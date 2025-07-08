package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import java.util.List;
import java.util.stream.Collectors;

public class GetTasksByUserUseCase {
    private final TaskRepository taskRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    public GetTasksByUserUseCase(TaskRepository taskRepository, UserRepository userRepository,
                                ProjectRepository projectRepository) {
        this.taskRepository = taskRepository;
        this.userRepository = userRepository;
        this.projectRepository = projectRepository;
    }

    public List<TaskOutputData> execute(String userId) {
        // Validate user exists
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found: " + userId));

        // Get tasks
        List<Task> tasks = taskRepository.findByUserId(userId);

        // Map to output data
        return tasks.stream()
                .map(task -> {
                    Project project = projectRepository.findById(task.getProjectId())
                            .orElseThrow(() -> new ProjectNotFoundException("Project not found: " + task.getProjectId()));
                    return new TaskOutputData(
                            task.getId(),
                            task.getTitle(),
                            task.getDescription(),
                            task.getDueDate(),
                            task.getStatus(),
                            task.getPriority(),
                            task.getAssignedUserId(),
                            user.getFullName(),
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