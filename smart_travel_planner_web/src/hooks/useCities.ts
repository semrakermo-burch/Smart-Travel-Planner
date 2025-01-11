import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { createCity, fetchCities } from "../api/citiesApi";
import { City } from "../types/City";

export const useCities = () => {
    return useQuery(
        {
            queryKey: ["cities"],
            queryFn: () => fetchCities()
        }
    );
};

export const useCreateCity = () => {
    const queryClient = useQueryClient();

    return useMutation<City, Error, Partial<City>>({
        mutationFn: createCity,
        onSuccess: () => {
            queryClient.invalidateQueries({ queryKey: ["cities"] }); // Refresh city list
        },
    });
};
