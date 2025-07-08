package com.taskmanager.usecases.ports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when a user is not authorized to perform an action
 */
public class UnauthorizedAccessException extends RuntimeException {
    public UnauthorizedAccessException(String message) {
        super(message);
    }
}