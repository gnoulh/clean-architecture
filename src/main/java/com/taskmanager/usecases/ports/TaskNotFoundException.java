package com.taskmanager.usecases.ports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when a task is not found
 */
public class TaskNotFoundException extends RuntimeException {
    public TaskNotFoundException(String message) {
        super(message);
    }
}