package ibu.aisi.smart_travel_planner.core.service;

import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.repository.TripRepository;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TripService {

    private final TripRepository tripRepository;
    private final ModelMapper mapper;

    public TripService(TripRepository tripRepository, ModelMapper mapper) {
        this.tripRepository = tripRepository;
        this.mapper = mapper;
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

    public TripDto createTrip(TripDto tripDTO) {
        Trip trip = Trip.builder()
                .name(tripDTO.getName())
                .description(tripDTO.getDescription())
                .startDate(tripDTO.getStartDate())
                .endDate(tripDTO.getEndDate())
                .status(tripDTO.getStatus())
                .build();
        // Associate User and City with the trip (assuming existence checks are done elsewhere)
        tripRepository.save(trip);
        return mapToDto(trip);
    }

    public void deleteTrip(Long id) {
        tripRepository.deleteById(id);
    }

    public List<TripDto> getTripsByUserId(Long userId) {
        return tripRepository.findByUserId(userId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    private TripDto mapToDto(Trip trip) {
        return mapper.map(trip, TripDto.class);
    }
}

