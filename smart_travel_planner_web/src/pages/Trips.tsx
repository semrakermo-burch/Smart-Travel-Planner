import { Box, Button, Container, Typography } from "@mui/material";
import React, { useState } from "react";
import { useSearchParams } from "react-router-dom";
import TripCard from "../components/TripCard";
import TripModal from "../components/TripModal";
import {
  useCreateTrip,
  useDeleteTrip,
  useEditTrip,
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

  const [modalOpen, setModalOpen] = useState(false);
  const [editingTrip, setEditingTrip] = useState<Trip | null>(null);

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

  if (isLoading) return <Typography>Loading trips...</Typography>;
  if (error) return <Typography>Error fetching trips.</Typography>;

  return (
    <Container maxWidth="lg" sx={{ marginTop: 4 }}>
      <Typography variant="h4" color="primary" gutterBottom>
        Trips for {email}
      </Typography>
      <Button
        variant="contained"
        color="primary"
        sx={{ marginBottom: 3 }}
        onClick={handleCreate}
      >
        Create Trip
      </Button>
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
    </Container>
  );
};

export default Trips;
