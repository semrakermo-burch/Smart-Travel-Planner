import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import axios from "axios";
import { createTrip, deleteTrip, editTrip, fetchTripsByEmail, fetchWeather, suggestTrips, WeatherResponse } from "../api/tripsApi";
import { Trip } from "../types/Trip";

export const useTripsByEmail = (email: string) => {
    return useQuery(
        {
            queryKey: ["trips", email],
            queryFn: () => fetchTripsByEmail(email)
        }
    );
};

export const useDeleteTrip = () => {
    const queryClient = useQueryClient();

    return useMutation<void, Error, number>({
        mutationFn: deleteTrip, // Use mutationFn to correctly type the mutation function
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ['filteredTrips'] }); // Pass the query key as a string
        },
    });
};

export const useEditTrip = () => {
    const queryClient = useQueryClient();

    return useMutation<Trip, Error, Partial<Trip>>({
        mutationFn: editTrip,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["filteredTrips"] }); // Refresh trip list
        },
    });
};


export const useCreateTrip = () => {
    const queryClient = useQueryClient();

    return useMutation<Trip, Error, { trip: Partial<Trip>, email: string }>({
        mutationFn: ({ trip, email }) => createTrip(trip, email), // Pass both trip data and email to createTrip
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["filteredTrips"] }); // Refresh trip list
        },
    });
};

export const useSuggestTrips = () => {
    return useMutation<String, Error, string[]>({ mutationFn: suggestTrips });
};

export const useWeather = (tripId: number | null) => {
    return useQuery<WeatherResponse, Error>(
        {
            queryKey: ["weather", tripId],
            queryFn: () => fetchWeather(tripId!),

            enabled: !!tripId, // Only fetch when tripId is valid
            refetchOnWindowFocus: false, // Disable refetching on window focus
            refetchOnMount: false, // Optionally disable refetching on mount

        }
    );
};

export const useFilteredTrips = (
    email: string,
    filters: {
        name?: string;
        description?: string;
        startDate?: string;
        endDate?: string;
        cityId?: number;
        status?: string;
        sortBy?: string; // Add sortBy to the query
        sortDirection?: string; // Add sortDirection to the query
    }
) => {
    return useQuery<Trip[]>({
        queryKey: ["filteredTrips", email, filters],
        queryFn: async () => {
            const params = { ...filters }; // Add all filter parameters to the request
            const response = await axios.get(`http://localhost:8080/api/trips/filter/${email}`, { params });
            return response.data;
        },
        enabled: !!email, // Only fetch if email is provided
    }
    );
};