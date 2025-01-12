package ibu.aisi.smart_travel_planner.core.service;

import ibu.aisi.smart_travel_planner.core.api.tripsuggester.TripSuggester;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.model.User;
import ibu.aisi.smart_travel_planner.core.repository.CityRepository;
import ibu.aisi.smart_travel_planner.core.repository.TripRepository;
import ibu.aisi.smart_travel_planner.core.repository.UserRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final UserRepository userRepository;
    private final CityRepository cityRepository;
    private final ModelMapper mapper;
    private final TripSuggester tripSuggester;

    public TripService(TripRepository tripRepository, UserRepository userRepository, CityRepository cityRepository, ModelMapper mapper, TripSuggester tripSuggester) {
        this.tripRepository = tripRepository;
        this.userRepository = userRepository;
        this.cityRepository = cityRepository;
        this.mapper = mapper;
        this.tripSuggester = tripSuggester;
    }

    public List<TripDto> getAllTrips() {
        return tripRepository.findAll().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public TripDto getTripById(Long id) {
        return tripRepository.findById(id)
                .map(this::mapToDto)
                .orElseThrow(() -> new RuntimeException("Trip not found"));

    }

    public TripDto createTrip(TripDto tripDTO, String email) {
        Trip trip = Trip.builder()
                .name(tripDTO.getName())
                .description(tripDTO.getDescription())
                .startDate(tripDTO.getStartDate())
                .endDate(tripDTO.getEndDate())
                .status(tripDTO.getStatus())
                .build();

        Optional<User> user = userRepository.findByEmail(email);
        user.ifPresent(trip::setUser);

        Optional<City> city = cityRepository.findById(tripDTO.getCityId());
        city.ifPresent(trip::setCity);

        trip = tripRepository.save(trip);
        return mapToDto(trip);
    }

    public TripDto updateTrip(Long id, TripDto tripDto) {
        Trip trip = tripRepository.findById(id).orElseThrow(() -> new RuntimeException("Trip not found"));
        trip.setName(tripDto.getName());
        trip.setDescription(tripDto.getDescription());
        trip.setStartDate(tripDto.getStartDate());
        trip.setEndDate(tripDto.getEndDate());
        trip.setStatus(tripDto.getStatus());
        return mapToDto(tripRepository.save(trip));
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public List<TripDto> getTripsByUserEmail(String email) {
        return tripRepository.findByUserEmailOrderByStartDateDesc(email).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public String suggestTrip(List<String> interests) {
        return tripSuggester.suggestTrip(interests);
    }

    private TripDto mapToDto(Trip trip) {
        return mapper.map(trip, TripDto.class);
    }
}

