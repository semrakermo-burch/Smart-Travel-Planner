import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { createTrip, deleteTrip, editTrip, fetchTripsByEmail, suggestTrips } from "../api/tripsApi";
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
            queryClient.invalidateQueries({ queryKey: ['trips'] }); // Pass the query key as a string
        },
    });
};

export const useEditTrip = () => {
    const queryClient = useQueryClient();

    return useMutation<Trip, Error, Partial<Trip>>({
        mutationFn: editTrip,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["trips"] }); // Refresh trip list
        },
    });
};


export const useCreateTrip = () => {
    const queryClient = useQueryClient();

    return useMutation<Trip, Error, { trip: Partial<Trip>, email: string }>({
        mutationFn: ({ trip, email }) => createTrip(trip, email), // Pass both trip data and email to createTrip
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["trips"] }); // Refresh trip list
        },
    });
};

export const useSuggestTrips = () => {
    return useMutation<String, Error, string[]>({ mutationFn: suggestTrips });
};