package ibu.aisi.smart_travel_planner.core.service;

import ibu.aisi.smart_travel_planner.core.dto.CityDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.repository.CityRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final CityRepository cityRepository;
    private final ModelMapper mapper;

    public CityService(CityRepository cityRepository, ModelMapper mapper) {
        this.cityRepository = cityRepository;
        this.mapper = mapper;
    }

    public List<CityDto> getAllCities() {
        return cityRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<CityDto> getCityById(Long id) {
        return cityRepository.findById(id)
                .map(this::mapToDto);
    }

    public Optional<CityDto> getCityByName(String name) {
        return cityRepository.findByName(name)
                .map(this::mapToDto);
    }

    public CityDto createCity(CityDto cityDTO) {
        City city = City.builder()
                .name(cityDTO.getName())
                .country(cityDTO.getCountry())
                .build();
        City savedCity = cityRepository.save(city);
        return mapToDto(savedCity);
    }

    public void deleteCity(Long id) {
        cityRepository.deleteById(id);
    }

    private CityDto mapToDto(City city) {
        return mapper.map(city, CityDto.class);
    }
}
