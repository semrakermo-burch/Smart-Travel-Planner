package ibu.aisi.smart_travel_planner.rest.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import ibu.aisi.smart_travel_planner.core.dto.CityDto;
import ibu.aisi.smart_travel_planner.core.service.CityService;
import ibu.aisi.smart_travel_planner.rest.controller.CityController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCities() {
        // Arrange
        CityDto cityDto = new CityDto(1L, "Paris", "France");
        when(cityService.getAllCities()).thenReturn(List.of(cityDto));

        // Act
        ResponseEntity<List<CityDto>> response = cityController.getAllCities();

        // Assert
        assertNotNull(response);
        assertEquals(1, response.getBody().size());
        assertEquals("Paris", response.getBody().get(0).getName());
        verify(cityService, times(1)).getAllCities();
    }

    @Test
    void testGetCityById_Found() {
        // Arrange
        CityDto cityDto = new CityDto(1L, "Paris", "France");
        when(cityService.getCityById(1L)).thenReturn(Optional.of(cityDto));

        // Act
        ResponseEntity<CityDto> response = cityController.getCityById(1L);

        // Assert
        assertNotNull(response);
        assertEquals("Paris", response.getBody().getName());
        verify(cityService, times(1)).getCityById(1L);
    }

    @Test
    void testGetCityById_NotFound() {
        // Arrange
        when(cityService.getCityById(1L)).thenReturn(Optional.empty());

        // Act
        ResponseEntity<CityDto> response = cityController.getCityById(1L);

        // Assert
        assertEquals(404, response.getStatusCodeValue());
        verify(cityService, times(1)).getCityById(1L);
    }

    @Test
    void testCreateCity() {
        // Arrange
        CityDto cityDto = new CityDto(null, "Paris", "France");
        CityDto savedCity = new CityDto(1L, "Paris", "France");
        when(cityService.createCity(cityDto)).thenReturn(savedCity);

        // Act
        ResponseEntity<CityDto> response = cityController.createCity(cityDto);

        // Assert
        assertNotNull(response);
        assertEquals(1L, response.getBody().getId());
        assertEquals("Paris", response.getBody().getName());
        verify(cityService, times(1)).createCity(cityDto);
    }

    @Test
    void testDeleteCity() {
        // Act
        ResponseEntity<Void> response = cityController.deleteCity(1L);

        // Assert
        assertEquals(204, response.getStatusCodeValue());
        verify(cityService, times(1)).deleteCity(1L);
    }
}
