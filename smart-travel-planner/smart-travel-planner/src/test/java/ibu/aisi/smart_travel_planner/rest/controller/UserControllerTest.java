package ibu.aisi.smart_travel_planner.rest.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ibu.aisi.smart_travel_planner.core.dto.UserDto;
import ibu.aisi.smart_travel_planner.core.service.UserService;
import ibu.aisi.smart_travel_planner.rest.controller.UserController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        // Arrange
        UserDto userDto = new UserDto(1L, "test@example.com", "John", "Doe");
        when(userService.getUserById(1L)).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.getUserById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("John", response.getBody().getFirstName());
        verify(userService, times(1)).getUserById(1L);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        UserDto userDto = new UserDto(1L, "test@example.com", "John", "Doe");
        when(userService.getUserByEmail("test@example.com")).thenReturn(userDto);

        // Act
        ResponseEntity<UserDto> response = userController.getUserByEmail("test@example.com");

        // Assert
        assertNotNull(response);
        assertEquals("test@example.com", response.getBody().getEmail());
        verify(userService, times(1)).getUserByEmail("test@example.com");
    }

    @Test
    void testDeleteUser() {
        // Act
        ResponseEntity<Void> response = userController.deleteUser(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(userService, times(1)).deleteUser(1L);
    }
}
