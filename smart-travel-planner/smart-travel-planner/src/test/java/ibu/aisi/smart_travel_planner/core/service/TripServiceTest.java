package ibu.aisi.smart_travel_planner.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.api.tripsuggester.TripSuggester;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.CityRepository;
import ibu.aisi.smart_travel_planner.core.repository.TripRepository;
import ibu.aisi.smart_travel_planner.core.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

class TripServiceTest {

    @Mock
    private TripRepository tripRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private TripSuggester tripSuggester;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private TripService tripService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllTrips() {
        // Arrange
        Trip trip = new Trip(1L, "Trip 1", "Description", null, null, "Upcoming", null, null);
        when(tripRepository.findAll()).thenReturn(List.of(trip));
        when(mapper.map(trip, TripDto.class)).thenReturn(new TripDto(1L, "Trip 1", "Description", null, null, "Upcoming", null, null,null));

        // Act
        List<TripDto> result = tripService.getAllTrips();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Trip 1", result.get(0).getName());
        verify(tripRepository, times(1)).findAll();
    }

    @Test
    void testCreateTrip() {
        // Arrange
        City city = new City(1L, "Paris", "France");
        TripDto tripDto = new TripDto(null, "Trip 1", "Description", null, null, "Upcoming", null, city.getId(), null);
        User user = new User(1L, "test@example.com", "John", "Doe");
        Trip trip = new Trip(null, "Trip 1", "Description", null, null, "Upcoming", user, city);

        when(userRepository.findByEmail("test@example.com")).thenReturn(Optional.of(user));
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(tripRepository.save(any(Trip.class))).thenReturn(trip);
        when(mapper.map(trip, TripDto.class)).thenReturn(tripDto);

        // Act
        TripDto result = tripService.createTrip(tripDto, "test@example.com");

        // Assert
        assertNotNull(result);
        assertEquals("Trip 1", result.getName());
        verify(tripRepository, times(1)).save(any(Trip.class));
    }

    @Test
    void testSuggestTrip() {
        // Arrange
        List<String> interests = List.of("beach", "hiking");
        when(tripSuggester.suggestTrip(interests)).thenReturn("Suggested Trip");

        // Act
        String result = tripService.suggestTrip(interests);

        // Assert
        assertEquals("Suggested Trip", result);
        verify(tripSuggester, times(1)).suggestTrip(interests);
    }
}

