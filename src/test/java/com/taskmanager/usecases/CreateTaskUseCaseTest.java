package com.taskmanager.usecases;

import com.taskmanager.entities.*;
import com.taskmanager.usecases.dto.*;
import com.taskmanager.usecases.ports.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.time.LocalDateTime;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CreateTaskUseCaseTest {

    @Mock
    private TaskRepository taskRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private ProjectRepository projectRepository;

    @InjectMocks
    private CreateTaskUseCase createTaskUseCase;

    @Test
    void testExecuteSuccess() {
        CreateTaskInputData inputData = new CreateTaskInputData(
                "Test Task", "Description", LocalDateTime.now().plusDays(1),
                "user1", "project1", TaskPriority.HIGH);

        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        Project project = new Project("Test Project", "Description", "user1", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        Task task = new Task("Test Task", "Description", LocalDateTime.now().plusDays(1), "user1", "project1", TaskPriority.HIGH);

        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(projectRepository.findById("project1")).thenReturn(Optional.of(project));
        when(taskRepository.save(any(Task.class))).thenReturn(task);

        TaskOutputData outputData = createTaskUseCase.execute(inputData);

        assertNotNull(outputData);
        assertEquals("Test Task", outputData.getTitle());
        assertEquals(TaskPriority.HIGH, outputData.getPriority());
        verify(taskRepository, times(1)).save(any(Task.class));
    }

    @Test
    void testExecuteUserNotFound() {
        CreateTaskInputData inputData = new CreateTaskInputData(
                "Test Task", "Description", LocalDateTime.now().plusDays(1),
                "user1", "project1", TaskPriority.HIGH);

        when(userRepository.findById("user1")).thenReturn(Optional.empty());

        assertThrows(UserNotFoundException.class, () -> createTaskUseCase.execute(inputData));
    }

    @Test
    void testExecuteProjectNotFound() {
        CreateTaskInputData inputData = new CreateTaskInputData(
                "Test Task", "Description", LocalDateTime.now().plusDays(1),
                "user1", "project1", TaskPriority.HIGH);

        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);

        when(userRepository.findById("user1")).thenReturn(Optional.of(user));
        when(projectRepository.findById("project1")).thenReturn(Optional.empty());

        assertThrows(ProjectNotFoundException.class, () -> createTaskUseCase.execute(inputData));
    }
}