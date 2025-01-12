package ibu.aisi.smart_travel_planner.core.repository;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.repository.CityRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class CityRepositoryTest {

    @Mock
    private CityRepository cityRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByName() {
        // Arrange
        String cityName = "Paris";
        City mockCity = new City(1L, cityName, "France");
        when(cityRepository.findByName(cityName)).thenReturn(Optional.of(mockCity));

        // Act
        Optional<City> result = cityRepository.findByName(cityName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(cityName, result.get().getName());
        verify(cityRepository, times(1)).findByName(cityName);
    }
}
