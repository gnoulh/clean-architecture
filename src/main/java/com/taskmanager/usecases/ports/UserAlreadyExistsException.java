package com.taskmanager.usecases.ports;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * Exception thrown when a user already exists
 */
public class UserAlreadyExistsException extends RuntimeException {
    public UserAlreadyExistsException(String message) {
        super(message);
    }
}