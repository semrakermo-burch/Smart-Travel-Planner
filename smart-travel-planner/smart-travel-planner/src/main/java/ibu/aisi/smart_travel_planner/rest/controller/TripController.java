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

    @GetMapping("/user/{email}")
    public ResponseEntity<List<TripDto>> getTripsByUserEmail(@PathVariable String email) {
        return ResponseEntity.ok(tripService.getTripsByUserEmail(email));
    }

    @PostMapping("/{email}")
    public ResponseEntity<TripDto> createTrip(@RequestBody TripDto tripDTO, @PathVariable String email) {
        return ResponseEntity.ok(tripService.createTrip(tripDTO, email));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TripDto> updateTrip(@RequestBody TripDto tripDTO, @PathVariable Long id) {
        return ResponseEntity.ok(tripService.updateTrip(id, tripDTO));
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
    public WeatherDto getWeatherForTrip(@PathVariable Long id) {
        // Fetch Trip (Assume trip exists)
        TripDto trip = tripService.getTripById(id);
        LocalDate startDate = trip.getStartDate().toInstant().atZone(java.time.ZoneId.systemDefault()).toLocalDate();
        return weatherService.getWeatherForecast(trip.getCity().getName(), startDate);
    }
    }


