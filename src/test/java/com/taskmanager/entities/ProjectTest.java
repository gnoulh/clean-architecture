package com.taskmanager.entities;

import org.junit.jupiter.api.Test;
import java.time.LocalDateTime;
import static org.junit.jupiter.api.Assertions.*;

class ProjectTest {
    @Test
    void testProjectCreation() {
        Project project = new Project("Test Project", "Description", "user1", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        assertNotNull(project.getId());
        assertEquals("Test Project", project.getName());
        assertEquals("Description", project.getDescription());
        assertEquals("user1", project.getOwnerId());
        assertEquals(ProjectStatus.PLANNING, project.getStatus());
        assertNotNull(project.getCreatedAt());
        assertNotNull(project.getUpdatedAt());
    }

    @Test
    void testUpdateDetails() {
        Project project = new Project("Test Project", "Description", "user1", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        project.updateDetails("New Name", "New Desc", LocalDateTime.now(), LocalDateTime.now().plusDays(20));
        assertEquals("New Name", project.getName());
        assertEquals("New Desc", project.getDescription());
    }

    @Test
    void testStartAndComplete() {
        Project project = new Project("Test Project", "Description", "user1", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        project.start();
        assertEquals(ProjectStatus.IN_PROGRESS, project.getStatus());
        project.complete();
        assertEquals(ProjectStatus.COMPLETED, project.getStatus());
    }

    @Test
    void testCancel() {
        Project project = new Project("Test Project", "Description", "user1", LocalDateTime.now(), LocalDateTime.now().plusDays(10));
        project.cancel();
        assertEquals(ProjectStatus.CANCELLED, project.getStatus());
    }

    @Test
    void testInvalidDateRangeThrows() {
        assertThrows(IllegalArgumentException.class, () ->
            new Project("Test Project", "Description", "user1", LocalDateTime.now().plusDays(10), LocalDateTime.now()));
    }
}
