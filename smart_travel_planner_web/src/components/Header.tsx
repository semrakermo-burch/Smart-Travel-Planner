import { Box, Button, TextField, Typography } from "@mui/material";
import React, { useState } from "react";

interface HeaderProps {
  onSuggestTrips: (interests: string) => void;
}

const Header: React.FC<HeaderProps> = ({ onSuggestTrips }) => {
  const [interests, setInterests] = useState<string>("");

  const handleSuggestTrips = () => {
    if (interests.trim()) {
      onSuggestTrips(interests);
    } else {
      alert("Please enter interests for suggestions.");
    }
  };

  return (
    <Box
      sx={{
        display: "flex",
        justifyContent: "space-between",
        alignItems: "center",
        padding: 2,
        backgroundColor: "primary.main",
        color: "white",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
      }}
    >
      <Typography variant="h6" color="inherit">
        Smart Travel Planner
      </Typography>
      <Box sx={{ display: "flex", gap: 2, alignItems: "center" }}>
        <TextField
          variant="outlined"
          size="small"
          placeholder="Enter interests"
          value={interests}
          onChange={(e) => setInterests(e.target.value)}
          sx={{
            backgroundColor: "white",
            borderRadius: 1,
            width: "200px",
          }}
        />
        <Button
          variant="contained"
          color="secondary"
          onClick={handleSuggestTrips}
        >
          Suggest Trips
        </Button>
      </Box>
    </Box>
  );
};

export default Header;
