package ibu.aisi.smart_travel_planner.rest.controller;

import ibu.aisi.smart_travel_planner.api.impl.weatherapi.WeatherApiWeatherForecast;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.dto.WeatherDto;
import ibu.aisi.smart_travel_planner.core.service.TripService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/trips")
public class TripController {

    private final TripService tripService;
    private final WeatherApiWeatherForecast weatherService;

    public TripController(TripService tripService, WeatherApiWeatherForecast weatherService) {
        this.tripService = tripService;
        this.weatherService = weatherService;
    }

    @GetMapping
    public ResponseEntity<List<TripDto>> getAllTrips() {
        return ResponseEntity.ok(tripService.getAllTrips());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TripDto> getTripById(@PathVariable Long id) {
        TripDto tripDto =  tripService.getTripById(id);
        return tripDto != null ? ResponseEntity.ok(tripDto) : ResponseEntity.notFound().build();
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<TripDto>> getTripsByUserId(@PathVariable Long userId) {
        return ResponseEntity.ok(tripService.getTripsByUserId(userId));
    }

    @PostMapping
    public ResponseEntity<TripDto> createTrip(@RequestBody TripDto tripDTO) {
        return ResponseEntity.ok(tripService.createTrip(tripDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrip(@PathVariable Long id) {
        tripService.deleteTrip(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/suggestion")
    public ResponseEntity<String> suggestTrip(@RequestBody List<String> interests) {
        return ResponseEntity.ok(tripService.suggestTrip(interests));
    }

    @GetMapping("/{id}/weather-forecast")
    public WeatherDto getWeatherForTrip(Long tripId) {
        // Fetch Trip (Assume trip exists)
        TripDto trip = tripService.getTripById(tripId);
        LocalDate startDate = trip.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return weatherService.getWeatherForecast(trip.getCity().getName(), startDate);
    }
    }


