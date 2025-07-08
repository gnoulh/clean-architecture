package com.taskmanager.adapters.controllers;

import com.taskmanager.usecases.*;
import com.taskmanager.usecases.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final CreateUserUseCase createUserUseCase;

    public UserController(CreateUserUseCase createUserUseCase) {
        this.createUserUseCase = createUserUseCase;
    }

    @PostMapping
    public ResponseEntity<UserOutputData> createUser(@Valid @RequestBody CreateUserInputData inputData) {
        UserOutputData result = createUserUseCase.execute(inputData);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}