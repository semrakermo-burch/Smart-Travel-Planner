import { City } from "./City";

export type Trip = {
    id: number;           // Unique identifier for the trip
    name: string;         // Name of the trip
    description: string;  // Description of the trip
    startDate: string;    // Start date of the trip (ISO 8601 format)
    endDate: string;      // End date of the trip (ISO 8601 format)
    status: string;       // Status of the trip (e.g., "Upcoming", "Completed")
    city: City;           // Associated city object
    cityId?: number;      // Optional property for handling form data
    userId?: number;      // Optional property for handling form data
}