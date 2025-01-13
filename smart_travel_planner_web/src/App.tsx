import React, { useState } from "react";
import { Route, Routes, useLocation } from "react-router-dom";
import Header from "./components/Header";
import TripSuggestionModal from "./components/TripSuggestionModal";
import { useSuggestTrips } from "./hooks/useTrips";
import Home from "./pages/Home";
import Trips from "./pages/Trips";

const App: React.FC = () => {
  const location = useLocation();
  const isHomePage = location.pathname === "/";

  const suggestTripsMutation = useSuggestTrips();

  const [suggestionModalOpen, setSuggestionModalOpen] = useState(false);
  const [tripSuggestion, setTripSuggestion] = useState<string>(""); // Holds the suggestion string

  const handleSuggestTrips = (interests: string) => {
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

  return (
    <>
      {!isHomePage && <Header onSuggestTrips={handleSuggestTrips} />}
      <Routes>
        <Route path="/" element={<Home />} />
        <Route
          path="/trips"
          element={
            <>
              <TripSuggestionModal
                open={suggestionModalOpen}
                onClose={() => setSuggestionModalOpen(false)}
                suggestion={tripSuggestion} // Pass the suggestion string
              />
              <Trips />
            </>
          }
        />
      </Routes>
    </>
  );
};

export default App;
