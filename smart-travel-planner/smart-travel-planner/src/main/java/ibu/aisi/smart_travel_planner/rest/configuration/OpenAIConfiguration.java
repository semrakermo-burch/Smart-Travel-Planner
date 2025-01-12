package ibu.aisi.smart_travel_planner.rest.configuration;

import com.theokanning.openai.service.OpenAiService;
import ibu.aisi.smart_travel_planner.api.impl.openai.OpenAITripSuggester;
import ibu.aisi.smart_travel_planner.core.api.tripsuggester.TripSuggester;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenAIConfiguration {
    @Value("${openai.secret}")
    private String apiSecret;

    @Bean
    public TripSuggester tripSuggester() {
        return new OpenAITripSuggester(this.openAiService());
    }

    @Bean
    public OpenAiService openAiService() {
        return new OpenAiService(this.apiSecret);
    }
}