package com.taskmanager;

import com.taskmanager.usecases.*;
import com.taskmanager.usecases.ports.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class UseCaseConfig {

    @Bean
    public CreateTaskUseCase createTaskUseCase(TaskRepository taskRepository, 
                                              UserRepository userRepository, 
                                              ProjectRepository projectRepository) {
        return new CreateTaskUseCase(taskRepository, userRepository, projectRepository);
    }

    @Bean
    public UpdateTaskUseCase updateTaskUseCase(TaskRepository taskRepository, 
                                              UserRepository userRepository, 
                                              ProjectRepository projectRepository) {
        return new UpdateTaskUseCase(taskRepository, userRepository, projectRepository);
    }

    @Bean
    public DeleteTaskUseCase deleteTaskUseCase(TaskRepository taskRepository) {
        return new DeleteTaskUseCase(taskRepository);
    }

    @Bean
    public GetTaskByIdUseCase getTaskByIdUseCase(TaskRepository taskRepository, 
                                                UserRepository userRepository, 
                                                ProjectRepository projectRepository) {
        return new GetTaskByIdUseCase(taskRepository, userRepository, projectRepository);
    }

    @Bean
    public GetTasksByUserUseCase getTasksByUserUseCase(TaskRepository taskRepository, 
                                                      UserRepository userRepository, 
                                                      ProjectRepository projectRepository) {
        return new GetTasksByUserUseCase(taskRepository, userRepository, projectRepository);
    }

    @Bean
    public GetTasksByProjectUseCase getTasksByProjectUseCase(TaskRepository taskRepository, 
                                                           UserRepository userRepository, 
                                                           ProjectRepository projectRepository) {
        return new GetTasksByProjectUseCase(taskRepository, userRepository, projectRepository);
    }

    @Bean
    public CreateUserUseCase createUserUseCase(UserRepository userRepository) {
        return new CreateUserUseCase(userRepository);
    }

    @Bean
    public CreateProjectUseCase createProjectUseCase(ProjectRepository projectRepository, 
                                                    UserRepository userRepository) {
        return new CreateProjectUseCase(projectRepository, userRepository);
    }

    @Bean
    public UpdateTaskStatusUseCase updateTaskStatusUseCase(TaskRepository taskRepository, 
                                                         UserRepository userRepository, 
                                                         ProjectRepository projectRepository) {
        return new UpdateTaskStatusUseCase(taskRepository, userRepository, projectRepository);
    }
}