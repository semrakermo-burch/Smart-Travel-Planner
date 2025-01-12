package ibu.aisi.smart_travel_planner.rest.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import ibu.aisi.smart_travel_planner.core.dto.CityDto;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.dto.WeatherDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.service.TripService;
import ibu.aisi.smart_travel_planner.rest.controller.TripController;
import ibu.aisi.smart_travel_planner.core.api.weatherforecast.WeatherForecast;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

class TripControllerTest {

    @Mock
    private TripService tripService;

    @Mock
    private WeatherForecast weatherService;

    @InjectMocks
    private TripController tripController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetTripsByUserEmail() {
        // Arrange
        TripDto tripDto = new TripDto(1L, "Trip 1", "Description", null, null, "Upcoming", 1L, null, null);
        when(tripService.getTripsByUserEmail("user@example.com")).thenReturn(List.of(tripDto));

        // Act
        ResponseEntity<List<TripDto>> response = tripController.getTripsByUserEmail("user@example.com");

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Trip 1", response.getBody().get(0).getName());
        verify(tripService, times(1)).getTripsByUserEmail("user@example.com");
    }

    @Test
    void testCreateTrip() {
        // Arrange
        TripDto tripDto = new TripDto(null, "Trip 1", "Description", null, null, "Upcoming", 1L, null, null);
        when(tripService.createTrip(tripDto, "user@example.com")).thenReturn(tripDto);

        // Act
        ResponseEntity<TripDto> response = tripController.createTrip(tripDto, "user@example.com");

        // Assert
        assertNotNull(response);
        assertEquals("Trip 1", response.getBody().getName());
        verify(tripService, times(1)).createTrip(tripDto, "user@example.com");
    }

    @Test
    void testDeleteTrip() {
        // Act
        ResponseEntity<Void> response = tripController.deleteTrip(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(tripService, times(1)).deleteTrip(1L);
    }
}
