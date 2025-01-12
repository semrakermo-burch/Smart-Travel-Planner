import {
    Box,
    Button,
    CircularProgress,
    Modal,
    Typography,
} from "@mui/material";
import React from "react";
import { WeatherResponse } from "../api/tripsApi";

interface WeatherModalProps {
  open: boolean;
  onClose: () => void;
  weatherData: WeatherResponse | null;
  loading: boolean;
  error: boolean;
}

const WeatherModal: React.FC<WeatherModalProps> = ({
  open,
  onClose,
  weatherData,
  loading,
  error,
}) => {
  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: "400px",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}
      >
        <Typography variant="h6" gutterBottom>
          Weather Information
        </Typography>
        {loading && <CircularProgress />}
        {error && (
          <Typography color="error">Failed to fetch weather data.</Typography>
        )}
        {weatherData && (
          <>
            <Typography>
              <strong>City:</strong> {weatherData.city}
            </Typography>
            <Typography>
              <strong>Date:</strong> {weatherData.date}
            </Typography>
            <Typography>
              <strong>Temperature:</strong> {weatherData.temperature}°C
            </Typography>
            <Typography>
              <strong>Condition:</strong> {weatherData.condition}
            </Typography>
          </>
        )}
        <Box sx={{ textAlign: "right", marginTop: 2 }}>
          <Button variant="contained" onClick={onClose}>
            Close
          </Button>{" "}
        </Box>
      </Box>
    </Modal>
  );
};

export default WeatherModal;
