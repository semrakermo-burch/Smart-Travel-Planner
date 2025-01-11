import { Box, Button, Container, TextField, Typography } from "@mui/material";
import React, { useState } from "react";
import { useSearchParams } from "react-router-dom";
import TripCard from "../components/TripCard";
import TripModal from "../components/TripModal";
import TripSuggestionModal from "../components/TripSuggestionModal";
import {
  useCreateTrip,
  useDeleteTrip,
  useEditTrip,
  useSuggestTrips,
  useTripsByEmail,
} from "../hooks/useTrips";
import { Trip } from "../types/Trip";

const Trips: React.FC = () => {
  const [searchParams] = useSearchParams();
  const email = searchParams.get("email") || "";

  const { data: trips, isLoading, error } = useTripsByEmail(email);
  const createTripMutation = useCreateTrip();
  const editTripMutation = useEditTrip();
  const deleteTripMutation = useDeleteTrip();
  const suggestTripsMutation = useSuggestTrips();

  const [modalOpen, setModalOpen] = useState(false);
  const [suggestionModalOpen, setSuggestionModalOpen] = useState(false);
  const [tripSuggestion, setTripSuggestion] = useState<string>(""); // Holds the suggestion string
  const [editingTrip, setEditingTrip] = useState<Trip | null>(null);
  const [interests, setInterests] = useState<string>("");

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

  const handleSuggestTrips = () => {
    suggestTripsMutation.mutate(interests.split(","), {
      onSuccess: (suggestion) => {
        setTripSuggestion(suggestion.toString()); // Save the suggestion string
        setSuggestionModalOpen(true); // Open the modal
      },
      onError: () => {
        alert("Failed to fetch suggestion. Please try again.");
      },
    });
  };

  if (isLoading) return <Typography>Loading trips...</Typography>;
  if (error) return <Typography>Error fetching trips.</Typography>;

  return (
    <Container maxWidth="lg" sx={{ marginTop: 4 }}>
      <Typography variant="h4" color="primary" gutterBottom>
        Trips for {email}
      </Typography>
      <Box sx={{ display: "flex", gap: 2, marginBottom: 3 }}>
        <Button variant="contained" color="primary" onClick={handleCreate}>
          Create Trip
        </Button>
        <TextField
          fullWidth
          placeholder="Enter interests (comma-separated)"
          value={interests}
          onChange={(e) => setInterests(e.target.value)}
        />
        <Button
          variant="outlined"
          color="secondary"
          onClick={handleSuggestTrips}
        >
          Suggest Trip
        </Button>
      </Box>
      {trips?.length ? (
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
      <TripSuggestionModal
        open={suggestionModalOpen}
        onClose={() => setSuggestionModalOpen(false)}
        suggestion={tripSuggestion} // Pass the suggestion string
      />
    </Container>
  );
};

export default Trips;
