import {
  Box,
  Button,
  Container,
  MenuItem,
  TextField,
  Typography,
} from "@mui/material";
import React, { useState } from "react";
import { useSearchParams } from "react-router-dom";
import TripCard from "../components/TripCard";
import TripModal from "../components/TripModal";
import WeatherModal from "../components/WeatherModal";
import { useCities } from "../hooks/useCities";
import {
  useCreateTrip,
  useDeleteTrip,
  useEditTrip,
  useFilteredTrips,
  useWeather,
} from "../hooks/useTrips";
import { Trip } from "../types/Trip";

const Trips: React.FC = () => {
  const [searchParams] = useSearchParams();
  const email = searchParams.get("email") || "";

  const [filters, setFilters] = useState({
    name: "",
    description: "",
    startDate: "",
    endDate: "",
    cityId: null,
    status: "",
    sortBy: "", // Sorting field
    sortDirection: "asc", // Sorting direction (default to ascending)
  });

  const filteredParams = {
    ...filters,
    cityId: filters.cityId ?? undefined, // Convert null to undefined
    sortBy: filters.sortBy || undefined, // Ensure blank values are not sent
    sortDirection: filters.sortDirection || "asc",
  };

  const {
    data: trips,
    isLoading,
    error,
  } = useFilteredTrips(email, filteredParams);
  const { data: cities } = useCities(); // Fetch cities for dropdown
  const createTripMutation = useCreateTrip();
  const editTripMutation = useEditTrip();
  const deleteTripMutation = useDeleteTrip();

  const [modalOpen, setModalOpen] = useState(false);
  const [editingTrip, setEditingTrip] = useState<Trip | null>(null);
  const [selectedTripId, setSelectedTripId] = useState<number | null>(null);

  const {
    data: weatherData,
    isLoading: weatherLoading,
    error: weatherError,
  } = useWeather(selectedTripId);

  const handleFilterChange = (key: keyof typeof filters, value: any) => {
    setFilters((prev) => ({ ...prev, [key]: value }));
  };

  const handleDelete = (tripId: number) => deleteTripMutation.mutate(tripId);

  const handleEdit = (trip: Trip) => {
    setEditingTrip(trip);
    setModalOpen(true);
  };

  const handleCreate = () => {
    setEditingTrip(null);
    setModalOpen(true);
  };

  const handleSave = (trip: Partial<Trip>) => {
    const tripData = { ...trip };
    if (editingTrip) {
      editTripMutation.mutate(tripData);
    } else {
      createTripMutation.mutate({ trip: tripData, email: email });
    }
    setModalOpen(false);
  };

  const handleCheckWeather = (tripId: number) => {
    if (!tripId) {
      alert("Invalid trip ID provided. Cannot fetch weather data.");
      return;
    }
    setSelectedTripId(tripId); // Set the trip ID for fetching weather
  };

  return (
    <Container maxWidth="lg" sx={{ marginTop: 4 }}>
      <Box
        sx={{
          display: "flex",
          marginBottom: 2,
          alignItems: "center",
          justifyContent: "space-between",
        }}
      >
        <Typography
          variant="h4"
          color="primary"
          gutterBottom
          sx={{ marginBottom: 2 }}
        >
          Trips for {email}
        </Typography>
        <Button variant="contained" color="secondary" onClick={handleCreate}>
          Create Trip
        </Button>
      </Box>
      <Box
        sx={{
          display: "flex",
          gap: 2,
          marginBottom: 3,
          alignItems: "center",
          width: "100%",
          justifyContent: "space-between",
        }}
      >
        <TextField
          variant="outlined"
          size="small"
          placeholder="Search by name"
          value={filters.name}
          onChange={(e) => handleFilterChange("name", e.target.value)}
          label="Name" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
        />
        <TextField
          variant="outlined"
          size="small"
          placeholder="Search by description"
          value={filters.description}
          onChange={(e) => handleFilterChange("description", e.target.value)}
          label="Description" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
        />
        <TextField
          type="date"
          size="small"
          label="Start Date" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
          value={filters.startDate || ""}
          onChange={(e) => handleFilterChange("startDate", e.target.value)}
        />
        <TextField
          type="date"
          size="small"
          label="End Date" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
          value={filters.endDate || ""}
          onChange={(e) => handleFilterChange("endDate", e.target.value)}
        />
        <TextField
          select
          size="small"
          value={filters.cityId !== null ? filters.cityId : ""} // Show "All Cities" by default
          onChange={(e) =>
            handleFilterChange(
              "cityId",
              e.target.value ? Number(e.target.value) : null
            )
          }
          label="City" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
          sx={{ minWidth: "80px" }}
        >
          <MenuItem value="">All Cities</MenuItem> {/* Default option */}
          {cities?.map((city) => (
            <MenuItem key={city.id} value={city.id}>
              {city.name}
            </MenuItem>
          ))}
        </TextField>
        <TextField
          select
          size="small"
          value={filters.status || ""} // Show "All Statuses" by default
          onChange={(e) => handleFilterChange("status", e.target.value)}
          label="Status" // Use label instead of placeholder
          InputLabelProps={{ shrink: true }} // Keep label visible even when value is set
          sx={{ minWidth: "80px" }}
        >
          <MenuItem value="">All Statuses</MenuItem> {/* Default option */}
          <MenuItem value="Upcoming">Upcoming</MenuItem>
          <MenuItem value="Completed">Completed</MenuItem>
        </TextField>

        <TextField
          select
          size="small"
          value={filters.sortBy}
          onChange={(e) => handleFilterChange("sortBy", e.target.value)}
          label="Sort By"
          InputLabelProps={{ shrink: true }}
          sx={{ minWidth: "80px" }}
        >
          <MenuItem value="">None</MenuItem> {/* Default option */}
          <MenuItem value="name">Name</MenuItem>
          <MenuItem value="description">Description</MenuItem>
          <MenuItem value="startDate">Start Date</MenuItem>
          <MenuItem value="endDate">End Date</MenuItem>
          <MenuItem value="status">Status</MenuItem>
          <MenuItem value="cityId">City</MenuItem>
        </TextField>
        <TextField
          select
          size="small"
          value={filters.sortDirection}
          onChange={(e) => handleFilterChange("sortDirection", e.target.value)}
          label="Sort Direction"
          InputLabelProps={{ shrink: true }}
          sx={{ minWidth: "150px" }}
        >
          <MenuItem value="asc">Ascending</MenuItem>
          <MenuItem value="desc">Descending</MenuItem>
        </TextField>
      </Box>
      {/* Render trips */}
      {isLoading ? (
        <Typography>Loading trips...</Typography>
      ) : error ? (
        <Typography>Error fetching trips.</Typography>
      ) : trips?.length ? (
        <Box
          sx={{
            display: "grid",
            gridTemplateColumns: "repeat(auto-fill, minmax(300px, 1fr))",
            gap: "16px",
          }}
        >
          {trips.map((trip) => (
            <TripCard
              key={trip.id}
              trip={trip}
              onDelete={handleDelete}
              onEdit={handleEdit}
              onCheckWeather={handleCheckWeather}
            />
          ))}
        </Box>
      ) : (
        <Typography>No trips found for this email.</Typography>
      )}
      <TripModal
        open={modalOpen}
        onClose={() => setModalOpen(false)}
        onSave={handleSave}
        initialData={editingTrip || undefined}
      />
      <WeatherModal
        open={!!selectedTripId}
        onClose={() => setSelectedTripId(null)}
        weatherData={weatherData || null}
        loading={weatherLoading}
        error={!!weatherError}
      />
    </Container>
  );
};

export default Trips;
