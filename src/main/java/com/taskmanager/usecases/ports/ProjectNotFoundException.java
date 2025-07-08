package com.taskmanager.usecases.ports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when a project is not found
 */
public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(String message) {
        super(message);
    }
}