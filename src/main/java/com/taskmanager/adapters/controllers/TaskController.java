package com.taskmanager.adapters.controllers;

/**
 * TaskController handles HTTP requests for task management.
 * It is part of the 'Interface Adapters' layer in Clean Architecture.
 * This controller validates input, handles errors, and delegates to use cases.
 * All business logic is handled in the use case layer.
 */

import com.taskmanager.usecases.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@Validated
public class TaskController {
    private static final Logger log = LoggerFactory.getLogger(TaskController.class);

    private final CreateTaskUseCase createTaskUseCase;
    private final UpdateTaskUseCase updateTaskUseCase;
    private final DeleteTaskUseCase deleteTaskUseCase;
    private final GetTaskByIdUseCase getTaskByIdUseCase;
    private final GetTasksByUserUseCase getTasksByUserUseCase;
    private final GetTasksByProjectUseCase getTasksByProjectUseCase;

    public TaskController(CreateTaskUseCase createTaskUseCase,
                         UpdateTaskUseCase updateTaskUseCase,
                         DeleteTaskUseCase deleteTaskUseCase,
                         GetTaskByIdUseCase getTaskByIdUseCase,
                         GetTasksByUserUseCase getTasksByUserUseCase,
                         GetTasksByProjectUseCase getTasksByProjectUseCase) {
        this.createTaskUseCase = createTaskUseCase;
        this.updateTaskUseCase = updateTaskUseCase;
        this.deleteTaskUseCase = deleteTaskUseCase;
        this.getTaskByIdUseCase = getTaskByIdUseCase;
        this.getTasksByUserUseCase = getTasksByUserUseCase;
        this.getTasksByProjectUseCase = getTasksByProjectUseCase;
    }

    @PostMapping
    public ResponseEntity<TaskOutputData> createTask(@Valid @RequestBody CreateTaskInputData inputData) {
        TaskOutputData result = createTaskUseCase.execute(inputData);
        return new ResponseEntity<>(result, HttpStatus.CREATED);
    }

    @PutMapping("/{taskId}")
    public ResponseEntity<TaskOutputData> updateTask(@PathVariable String taskId,
                                                    @Valid @RequestBody UpdateTaskInputData inputData) {
        UpdateTaskInputData updateData = new UpdateTaskInputData(
                taskId,
                inputData.getTitle(),
                inputData.getDescription(),
                inputData.getDueDate(),
                inputData.getPriority()
        );
        TaskOutputData result = updateTaskUseCase.execute(updateData);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @DeleteMapping("/{taskId}")
    public ResponseEntity<Void> deleteTask(@PathVariable String taskId) {
        deleteTaskUseCase.execute(taskId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping("/{taskId}")
    public ResponseEntity<TaskOutputData> getTaskById(@PathVariable String taskId) {
        TaskOutputData result = getTaskByIdUseCase.execute(taskId);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TaskOutputData>> getTasksByUser(@PathVariable String userId) {
        List<TaskOutputData> results = getTasksByUserUseCase.execute(userId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<TaskOutputData>> getTasksByProject(@PathVariable String projectId) {
        List<TaskOutputData> results = getTasksByProjectUseCase.execute(projectId);
        return new ResponseEntity<>(results, HttpStatus.OK);
    }
}