package ibu.aisi.smart_travel_planner.core.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

@Data
public class WeatherAPIResponse {

    private Forecast forecast;

    @Data
    public static class Forecast {
        @JsonProperty("forecastday")
        private List<ForecastDay> forecastDay;

        @Data
        public static class ForecastDay {
            private Day day;

            @Data
            public static class Day {
                @JsonProperty("avgtemp_c")
                private double avgTempC;
                private Condition condition;

                @Data
                public static class Condition {
                    private String text;
                }
            }
        }
    }
}
