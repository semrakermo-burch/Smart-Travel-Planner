package ibu.aisi.smart_travel_planner.core.api.tripsuggester;

import java.util.List;

public interface TripSuggester {
    String suggestTrip(List<String> interests);
}