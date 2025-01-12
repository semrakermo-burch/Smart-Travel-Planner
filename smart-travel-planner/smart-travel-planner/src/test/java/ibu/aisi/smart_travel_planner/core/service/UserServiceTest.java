package ibu.aisi.smart_travel_planner.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.dto.UserDto;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.UserRepository;
import ibu.aisi.smart_travel_planner.core.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.Optional;

class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetUserById() {
        // Arrange
        User user = new User(1L, "test@example.com", "John", "Doe");
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(new UserDto(1L, "test@example.com", "John", "Doe"));

        // Act
        UserDto result = userService.getUserById(1L);

        // Assert
        assertNotNull(result);
        assertEquals("John", result.getFirstName());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    void testGetUserByEmail() {
        // Arrange
        User user = new User(1L, "test@example.com", "John", "Doe");
        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(mapper.map(user, UserDto.class)).thenReturn(new UserDto(1L, "test@example.com", "John", "Doe"));

        // Act
        UserDto result = userService.getUserByEmail("test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("test@example.com", result.getEmail());
        verify(userRepository, times(2)).findByEmail("test@example.com"); // Called twice in your implementation
    }

    @Test
    void testDeleteUser() {
        // Act
        userService.deleteUser(1L);

        // Assert
        verify(userRepository, times(1)).deleteById(1L);
    }
}
