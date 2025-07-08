package com.taskmanager.usecases.ports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when a user is not found
 */
public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super(message);
    }
}