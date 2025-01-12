package ibu.aisi.smart_travel_planner.api.impl.openai;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;
import ibu.aisi.smart_travel_planner.core.api.tripsuggester.TripSuggester;

import java.util.List;

public class OpenAITripSuggester implements TripSuggester {
    private final OpenAiService openAiService;

    public OpenAITripSuggester(OpenAiService openAiService) {
        this.openAiService = openAiService;
    }

    @Override
    public String suggestTrip(List<String> interests) {
        String formattedInterests = String.join(", ", interests);
        String prompt =
                "Suggest the user of a smart travel planner application a new trip to take in the following manner: " +
                "```" +
                "Location: (City, Country)" +
                "Activities: (List of activities the user can do there, max 3 activities with just their titles, not full sentences)" +
                "Reason: (Reason to take this trip and visit this city, max 1 sentence)" +
                "```" +
                "This suggestion should be based on the following user interests : " +
                formattedInterests;
        CompletionRequest completionRequest = CompletionRequest.builder()
                .prompt(prompt)
                .model("gpt-3.5-turbo-instruct")
                .maxTokens(80)
                .build();
        return openAiService.createCompletion(completionRequest).getChoices().get(0).getText().trim();
    }
}
