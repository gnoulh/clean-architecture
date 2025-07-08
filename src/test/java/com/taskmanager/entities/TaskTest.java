package com.taskmanager.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class TaskTest {

    @Test
    void testTaskCreation() {
        Task task = new Task("Test Task", "Description", LocalDateTime.now().plusDays(1), "user1", "project1", TaskPriority.HIGH);

        assertNotNull(task.getId());
        assertEquals("Test Task", task.getTitle());
        assertEquals("Description", task.getDescription());
        assertEquals(TaskStatus.TODO, task.getStatus());
        assertEquals(TaskPriority.HIGH, task.getPriority());
        assertEquals("user1", task.getAssignedUserId());
        assertEquals("project1", task.getProjectId());
        assertNotNull(task.getCreatedAt());
        assertNotNull(task.getUpdatedAt());
    }

    @Test
    void testUpdateDetails() {
        Task task = new Task("Old Title", "Old Desc", LocalDateTime.now().plusDays(1), "user1", "project1", TaskPriority.MEDIUM);
        
        task.updateDetails("New Title", "New Desc", LocalDateTime.now().plusDays(2), TaskPriority.LOW);
        assertEquals("New Title", task.getTitle());
        assertEquals("New Desc", task.getDescription());
        assertEquals(TaskPriority.LOW, task.getPriority());
    }

    @Test
    void testUpdateDetailsThrowsExceptionForCompletedTask() {
        Task task = new Task("Test Task", "Description", LocalDateTime.now().plusDays(1), "user1", "project1", TaskPriority.MEDIUM);
        task.markAsCompleted();
        assertThrows(IllegalStateException.class, () -> 
                task.updateDetails("New Title", "New Desc", LocalDateTime.now().plusDays(2), TaskPriority.LOW));
    }

    @Test
    void testIsOverdue() {
        Task task = new Task("Test Task", "Description", LocalDateTime.now().minusDays(1), "user1", "project1", TaskPriority.MEDIUM);
        
        assertTrue(task.isOverdue());
    }
}
// No User objects are created in this file, so no password update is needed.