import { Box, Button, Modal, Typography } from "@mui/material";
import React from "react";

interface TripSuggestionModalProps {
  open: boolean;
  onClose: () => void;
  suggestion: string; // The suggestion string from the backend
}

const TripSuggestionModal: React.FC<TripSuggestionModalProps> = ({
  open,
  onClose,
  suggestion,
}) => {
  // Split the suggestion string by "Activities:" and "Reason:"
  const locationRegex = /Location:\s*(.*?)(?=\s*Activities:|$)/;
  const activitiesRegex = /Activities:\s*(.*?)(?=\s*Reason:|$)/;
  const reasonRegex = /Reason:\s*(.*)/;

  const location = (suggestion.match(locationRegex) || [])[1] || "";
  const activities = (suggestion.match(activitiesRegex) || [])[1] || "";
  const reason = (suggestion.match(reasonRegex) || [])[1] || "";

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: "50%",
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}
      >
        <Typography variant="h6" gutterBottom>
          Trip Suggestion
        </Typography>
        <Box sx={{ mb: 2 }}>
          <Typography variant="body1" sx={{ fontWeight: "bold" }}>
            Location:
          </Typography>
          <Typography variant="body2" sx={{ mb: 1 }}>
            {location}
          </Typography>
        </Box>
        <Box sx={{ mb: 2 }}>
          <Typography variant="body1" sx={{ fontWeight: "bold" }}>
            Activities:
          </Typography>
          <Typography variant="body2" sx={{ mb: 1 }}>
            {activities}
          </Typography>
        </Box>
        <Box sx={{ mb: 2 }}>
          <Typography variant="body1" sx={{ fontWeight: "bold" }}>
            Reason:
          </Typography>
          <Typography variant="body2">{reason}</Typography>
        </Box>
        <Box sx={{ textAlign: "right" }}>
          <Button variant="contained" onClick={onClose}>
            Close
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default TripSuggestionModal;
