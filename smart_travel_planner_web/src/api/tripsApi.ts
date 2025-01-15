import axios from "axios";
import { Trip } from "../types/Trip";

const API_BASE_URL = "https://smart-travel-planner-live.onrender.com/api/trips";

export const fetchTripsByEmail = async (email: string): Promise<Trip[]> => {
  const { data } = await axios.get(`${API_BASE_URL}/user/${email}`);
  return data;
};

export const deleteTrip = async (tripId: number): Promise<void> => {
  await axios.delete(`${API_BASE_URL}/${tripId}`);
};


export const createTrip = async (trip: Partial<Trip>, email: string): Promise<Trip> => {
  const { data } = await axios.post(`${API_BASE_URL}/${email}`, trip);
  return data;
};

export const editTrip = async (trip: Partial<Trip>): Promise<Trip> => {
  const { data } = await axios.put(`${API_BASE_URL}/${trip.id}`, trip);
  return data;
};

export const suggestTrips = async (interests: string[]): Promise<String> => {
  const { data } = await axios.post(`${API_BASE_URL}/suggestion`, interests);
  return data;
};

export interface WeatherResponse {
  city: string;
  date: string;
  temperature: number;
  condition: string;
}

export const fetchWeather = async (tripId: number): Promise<WeatherResponse> => {
  const { data } = await axios.get(`${API_BASE_URL}/${tripId}/weather-forecast`);
  return data;
};