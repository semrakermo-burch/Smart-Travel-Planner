package ibu.aisi.smart_travel_planner.core.api.weatherforecast;

import ibu.aisi.smart_travel_planner.core.dto.WeatherDto;

import java.time.LocalDate;

public interface WeatherForecast {
    WeatherDto getWeatherForecast(String cityName, LocalDate date);
}
