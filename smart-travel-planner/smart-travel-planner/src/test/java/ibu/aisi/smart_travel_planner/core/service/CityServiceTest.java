package ibu.aisi.smart_travel_planner.core.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.dto.CityDto;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.CityRepository;
import ibu.aisi.smart_travel_planner.core.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

class CityServiceTest {

    @Mock
    private CityRepository cityRepository;

    @Mock
    private ModelMapper mapper;

    @InjectMocks
    private CityService cityService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCities() {
        // Arrange
        City city = new City(1L, "Paris", "France");
        when(cityRepository.findAll()).thenReturn(List.of(city));
        when(mapper.map(city, CityDto.class)).thenReturn(new CityDto(1L, "Paris", "France"));

        // Act
        List<CityDto> result = cityService.getAllCities();

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Paris", result.get(0).getName());
        verify(cityRepository, times(1)).findAll();
    }

    @Test
    void testGetCityById() {
        // Arrange
        City city = new City(1L, "Paris", "France");
        when(cityRepository.findById(1L)).thenReturn(Optional.of(city));
        when(mapper.map(city, CityDto.class)).thenReturn(new CityDto(1L, "Paris", "France"));

        // Act
        Optional<CityDto> result = cityService.getCityById(1L);

        // Assert
        assertTrue(result.isPresent());
        assertEquals("Paris", result.get().getName());
        verify(cityRepository, times(1)).findById(1L);
    }

    @Test
    void testCreateCity() {
        // Arrange
        CityDto cityDto = new CityDto(null, "Paris", "France");
        City city = new City(null, "Paris", "France");

        when(cityRepository.save(any(City.class))).thenReturn(city);
        when(mapper.map(city, CityDto.class)).thenReturn(cityDto);

        // Act
        CityDto result = cityService.createCity(cityDto);

        // Assert
        assertNotNull(result);
        assertEquals("Paris", result.getName());
        verify(cityRepository, times(1)).save(any(City.class));
    }

    @Test
    void testDeleteCity() {
        // Act
        cityService.deleteCity(1L);

        // Assert
        verify(cityRepository, times(1)).deleteById(1L);
    }
}
