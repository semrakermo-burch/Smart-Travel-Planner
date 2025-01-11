import axios from "axios";
import { City } from "../types/City";

const API_BASE_URL = "http://localhost:8080/api/cities";

export const fetchCities = async (): Promise<City[]> => {
    const { data } = await axios.get(`${API_BASE_URL}`);
    return data;
};

export const createCity = async (city: Partial<City>): Promise<City> => {
    const { data } = await axios.post(`${API_BASE_URL}`, city);
    return data;
};
