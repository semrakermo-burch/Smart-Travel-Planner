import DeleteIcon from "@mui/icons-material/Delete";
import EditIcon from "@mui/icons-material/Edit";
import {
  Button,
  Card,
  CardActions,
  CardContent,
  IconButton,
  Typography,
} from "@mui/material";
import React from "react";
import { Trip } from "../types/Trip";

interface TripCardProps {
  trip: Trip;
  onDelete: (id: number) => void;
  onEdit: (trip: Trip) => void;
}

const formatDate = (dateString: string) => {
  const date = new Date(dateString);
  return new Intl.DateTimeFormat("en-GB").format(date); // Formats as DD.MM.YYYY
};

const TripCard: React.FC<TripCardProps> = ({ trip, onDelete, onEdit }) => {
  const handleDelete = () => onDelete(trip.id);
  const handleEdit = () => onEdit(trip);

  return (
    <Card
      sx={{
        backgroundColor: "background.paper",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
        borderRadius: "8px",
        transition: "transform 0.2s",
        "&:hover": {
          transform: "scale(1.02)",
        },
      }}
    >
      <CardContent>
        <Typography variant="h5" color="text.primary" gutterBottom>
          {trip.name}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {trip.description}
        </Typography>
        <Typography variant="body2" color="text.secondary">
          {formatDate(trip.startDate)} - {formatDate(trip.endDate)}
        </Typography>
      </CardContent>
      <CardActions>
        <IconButton color="primary" onClick={handleEdit}>
          <EditIcon />
        </IconButton>
        <Button
          size="small"
          color="secondary"
          variant="contained"
          startIcon={<DeleteIcon />}
          onClick={handleDelete}
          sx={{
            backgroundColor: "error.main",
            "&:hover": {
              backgroundColor: "error.dark",
            },
          }}
        >
          Delete
        </Button>
      </CardActions>
    </Card>
  );
};

export default TripCard;