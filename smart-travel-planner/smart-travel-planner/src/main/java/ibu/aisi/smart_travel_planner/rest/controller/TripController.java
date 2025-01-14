package ibu.aisi.smart_travel_planner.rest.controller;

import ibu.aisi.smart_travel_planner.api.impl.weatherapi.WeatherApiWeatherForecast;
import ibu.aisi.smart_travel_planner.core.dto.TripDto;
import ibu.aisi.smart_travel_planner.core.dto.WeatherDto;
import ibu.aisi.smart_travel_planner.core.model.City;
import ibu.aisi.smart_travel_planner.core.model.Trip;
import ibu.aisi.smart_travel_planner.core.service.TripService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
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

    @GetMapping("/filter/{email}")
    public ResponseEntity<List<TripDto>> filterAndSortTrips(
            @PathVariable String email,
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String description,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date startDate,
            @RequestParam(required = false)
            @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date endDate,
            @RequestParam(required = false) String status,
            @RequestParam(required = false) Long cityId,
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String sortDirection) {

        List<TripDto> tripsDtos = tripService.filterAndSortTrips(
                email, name, description, startDate, endDate, status, cityId, sortBy, sortDirection);

        return ResponseEntity.ok(tripsDtos);
    }

}


