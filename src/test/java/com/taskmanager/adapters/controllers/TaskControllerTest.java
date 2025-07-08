package com.taskmanager.adapters.controllers;

import com.taskmanager.usecases.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.entities.*;
import com.taskmanager.usecases.ports.UserRepository;
import com.taskmanager.usecases.ports.ProjectRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TaskControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ProjectRepository projectRepository;

    @Test
    void testCreateTask() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        userRepository.save(user);

        Project project = new Project("Test Project", "Description", user.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        projectRepository.save(project);

        CreateTaskInputData inputData = new CreateTaskInputData(
                "Test Task", "Description", LocalDateTime.now().plusDays(1),
                user.getId(), project.getId(), TaskPriority.MEDIUM);

        ResponseEntity<TaskOutputData> response = restTemplate.withBasicAuth("demo", "demo")
                .postForEntity("/api/tasks", inputData, TaskOutputData.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Test Task", response.getBody().getTitle());
    }

    @Test
    void testGetTasksByProject() {
        User user = new User("test2@example.com", "Jane", "Smith", "Password1!", UserRole.USER);
        userRepository.save(user);

        Project project = new Project("Test Project 2", "Description", user.getId(), LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        projectRepository.save(project);

        CreateTaskInputData inputData = new CreateTaskInputData(
                "Test Task", "Description", LocalDateTime.now().plusDays(1),
                user.getId(), project.getId(), TaskPriority.MEDIUM);

        restTemplate.withBasicAuth("demo", "demo")
                .postForEntity("/api/tasks", inputData, TaskOutputData.class);

        ResponseEntity<TaskOutputData[]> response = restTemplate.withBasicAuth("demo", "demo")
                .getForEntity("/api/tasks/project/" + project.getId(), TaskOutputData[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody().length > 0);
    }
}