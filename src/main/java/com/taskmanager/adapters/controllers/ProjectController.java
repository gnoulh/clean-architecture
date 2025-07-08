package com.taskmanager.adapters.controllers;

import com.taskmanager.usecases.*;
import com.taskmanager.usecases.dto.*;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * ProjectController is the entry point for HTTP requests related to projects.
 * It belongs to the 'Interface Adapters' layer in Clean Architecture.
 * This controller receives input from the web, validates it, and delegates to use cases.
 * It does not contain business logic, only orchestration and mapping.
 */
@RestController
@RequestMapping("/api/projects")
public class ProjectController {
    private final CreateProjectUseCase createProjectUseCase;

    public ProjectController(CreateProjectUseCase createProjectUseCase) {
        this.createProjectUseCase = createProjectUseCase;
    }

    @PostMapping
    public ResponseEntity<ProjectOutputData> createProject(@Valid @RequestBody CreateProjectInputData inputData) {
        ProjectOutputData result = createProjectUseCase.execute(inputData);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }
}