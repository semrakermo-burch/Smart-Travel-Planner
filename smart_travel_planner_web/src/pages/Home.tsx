import { Box, Button, Container, Typography } from "@mui/material";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Input from "../components/Input";

const Home: React.FC = () => {
  const [email, setEmail] = useState("");
  const navigate = useNavigate();

  const handleSubmit = () => {
    if (email) {
      navigate(`/trips?email=${encodeURIComponent(email)}`);
    } else {
      alert("Please enter a valid email.");
    }
  };

  const handleKeyDown = (e: React.KeyboardEvent) => {
    if (e.key === "Enter") {
      handleSubmit();
    }
  };

  return (
    <Container
      maxWidth="sm"
      sx={{
        textAlign: "center",
        marginTop: "10%",
        backgroundColor: "background.paper",
        padding: "2rem",
        borderRadius: "8px",
        boxShadow: "0 4px 12px rgba(0, 0, 0, 0.1)",
      }}
    >
      <Typography variant="h4" color="primary" gutterBottom>
        Welcome to Smart Travel Planner
      </Typography>
      <Typography variant="body1" color="text.secondary" gutterBottom>
        Enter your email to view your trips:
      </Typography>
      <Box sx={{ marginTop: 3 }}>
        <Input
          label="Email"
          value={email}
          onChange={(e: React.ChangeEvent<HTMLInputElement>) =>
            setEmail(e.target.value)
          }
          onKeyDown={handleKeyDown} // Replace onKeyPress with onKeyDown
        />
        <Button
          fullWidth
          variant="contained"
          color="primary"
          sx={{ marginTop: 3 }}
          onClick={handleSubmit}
        >
          View Trips
        </Button>
      </Box>
    </Container>
  );
};

export default Home;
