package ibu.aisi.smart_travel_planner.api.impl.weatherapi;

import ibu.aisi.smart_travel_planner.core.api.weatherforecast.WeatherForecast;
import ibu.aisi.smart_travel_planner.core.response.WeatherAPIResponse;
import org.springframework.beans.factory.annotation.Value;
import ibu.aisi.smart_travel_planner.core.dto.WeatherDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@Service
public class WeatherApiWeatherForecast implements WeatherForecast {

    private final RestTemplate restTemplate;
    private final String apiKey;
    private final String baseUrl;

    public WeatherApiWeatherForecast(RestTemplate restTemplate,
                              @Value("${weatherapi.key}") String apiKey,
                              @Value("${weatherapi.url}") String baseUrl) {
        this.restTemplate = restTemplate;
        this.apiKey = apiKey;
        this.baseUrl = baseUrl;
    }

    @Override
    public WeatherDto getWeatherForecast(String cityName, LocalDate date) {
        String url = String.format("%s/forecast.json?key=%s&q=%s&days=1", baseUrl, apiKey, cityName);
        WeatherAPIResponse response = restTemplate.getForObject(url, WeatherAPIResponse.class);

        if (response == null || response.getForecast() == null) {
            throw new RuntimeException("Weather data could not be retrieved.");
        }

        WeatherAPIResponse.Forecast.ForecastDay.Day forecast = response.getForecast()
                .getForecastDay()
                .get(0)
                .getDay();

        return WeatherDto.builder()
                .city(cityName)
                .date(date)
                .temperature(forecast.getAvgTempC())
                .condition(forecast.getCondition().getText())
                .build();
    }
}
