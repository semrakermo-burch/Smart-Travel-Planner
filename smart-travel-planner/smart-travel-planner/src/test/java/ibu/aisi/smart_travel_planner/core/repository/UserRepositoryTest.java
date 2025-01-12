package ibu.aisi.smart_travel_planner.core.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class UserRepositoryTest {

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByEmail() {
        // Arrange
        String email = "test@example.com";
        User mockUser = new User(1L, email, "Test", "User");
        when(userRepository.findByEmail(email)).thenReturn(Optional.of(mockUser));

        // Act
        Optional<User> result = userRepository.findByEmail(email);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(email, result.get().getEmail());
        assertEquals("Test", result.get().getFirstName());
        verify(userRepository, times(1)).findByEmail(email);
    }
}
