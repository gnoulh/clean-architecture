package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CreateUserUseCase {
    private static final Logger logger = LoggerFactory.getLogger(CreateUserUseCase.class);
    private final UserRepository userRepository;

    public CreateUserUseCase(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserOutputData execute(CreateUserInputData inputData) {
        logger.info("Creating user with email: {}", inputData.getEmail());

        // Validate email uniqueness
        if (userRepository.existsByEmail(inputData.getEmail())) {
            logger.warn("User already exists with email: {}", inputData.getEmail());
            throw new UserAlreadyExistsException("User already exists with email: " + inputData.getEmail());
        }

        // Create user entity
        User user = new User(
                inputData.getEmail(),
                inputData.getFirstName(),
                inputData.getLastName(),
                inputData.getPassword(),
                inputData.getRole()
        );

        // Save user
        User savedUser = userRepository.save(user);
        logger.info("User created successfully: {}", savedUser.getId());

        // Return output data
        return new UserOutputData(
                savedUser.getId(),
                savedUser.getEmail(),
                savedUser.getFirstName(),
                savedUser.getLastName(),
                savedUser.getRole(),
                savedUser.getFullName(),
                savedUser.getDisplayName(),
                savedUser.getCreatedAt(),
                savedUser.getLastLoginAt(),
                savedUser.isRecentlyActive()
        );
    }
}