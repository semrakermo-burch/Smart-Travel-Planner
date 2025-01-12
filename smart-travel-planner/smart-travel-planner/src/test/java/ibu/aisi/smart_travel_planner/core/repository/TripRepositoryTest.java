package ibu.aisi.smart_travel_planner.core.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.TripRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;

class TripRepositoryTest {

    @Mock
    private TripRepository tripRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByUserEmailOrderByStartDateDesc() {
        // Arrange
        String userEmail = "user@example.com";
        User mockUser = new User(1L, userEmail, "John", "Doe");
        City mockCity = new City(1L, "Rome", "Italy");

        Trip trip1 = new Trip(1L, "Trip 1", "Description 1",
                java.sql.Date.valueOf("2024-12-01"),
                java.sql.Date.valueOf("2024-12-05"),
                "Upcoming", mockUser, mockCity);

        Trip trip2 = new Trip(2L, "Trip 2", "Description 2",
                java.sql.Date.valueOf("2024-11-20"),
                java.sql.Date.valueOf("2024-11-25"),
                "Upcoming", mockUser, mockCity);

        when(tripRepository.findByUserEmailOrderByStartDateDesc(userEmail))
                .thenReturn(Arrays.asList(trip1, trip2));

        // Act
        List<Trip> result = tripRepository.findByUserEmailOrderByStartDateDesc(userEmail);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals("Trip 1", result.get(0).getName());
        assertEquals("Trip 2", result.get(1).getName());
        verify(tripRepository, times(1)).findByUserEmailOrderByStartDateDesc(userEmail);
    }
}
