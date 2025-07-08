package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.ports.*;

public class DeleteTaskUseCase {
    private final TaskRepository taskRepository;

    public DeleteTaskUseCase(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    public void execute(String taskId) {
        // Validate task exists
        if (!taskRepository.existsById(taskId)) {
            throw new TaskNotFoundException("Task not found: " + taskId);
        }

        // Delete task
        taskRepository.deleteById(taskId);
    }
}