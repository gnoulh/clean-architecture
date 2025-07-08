package com.taskmanager.entities;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UserTest {
    @Test
    void testUserCreation() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        assertNotNull(user.getId());
        assertEquals("test@example.com", user.getEmail());
        assertEquals("John", user.getFirstName());
        assertEquals("Doe", user.getLastName());
        assertEquals(UserRole.USER, user.getRole());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
        assertNotNull(user.getCreatedAt());
        assertNotNull(user.getUpdatedAt());
    }

    @Test
    void testUpdateProfile() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        user.updateProfile("Jane", "Smith", "jane@example.com");
        assertEquals("Jane", user.getFirstName());
        assertEquals("Smith", user.getLastName());
        assertEquals("jane@example.com", user.getEmail());
    }

    @Test
    void testChangePassword() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        user.changePassword("NewPass1!");
        assertEquals("NewPass1!", user.getPassword());
    }

    @Test
    void testDeactivateAndActivate() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        user.deactivate();
        assertEquals(UserStatus.INACTIVE, user.getStatus());
        user.activate();
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    void testMarkAsDeleted() {
        User user = new User("test@example.com", "John", "Doe", "Password1!", UserRole.USER);
        user.markAsDeleted();
        assertEquals(UserStatus.DELETED, user.getStatus());
    }

    @Test
    void testInvalidEmailThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User("bademail", "John", "Doe", "Password1!", UserRole.USER));
    }

    @Test
    void testInvalidPasswordThrows() {
        assertThrows(IllegalArgumentException.class, () -> new User("test@example.com", "John", "Doe", "short", UserRole.USER));
    }
}
