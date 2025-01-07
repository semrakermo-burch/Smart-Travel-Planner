package ibu.aisi.smart_travel_planner.core.dto;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;

@Builder
@Data
public class WeatherDto {
    private String city;
    private LocalDate date;
    private double temperature;
    private String condition;
}
