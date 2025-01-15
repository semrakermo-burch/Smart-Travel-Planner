import {
  Box,
  Button,
  MenuItem,
  Modal,
  Select,
  SelectChangeEvent,
  TextField,
  Typography,
} from "@mui/material";
import React, { useEffect, useState } from "react";
import { useCities, useCreateCity } from "../hooks/useCities";
import { Trip } from "../types/Trip";

interface TripModalProps {
  open: boolean;
  onClose: () => void;
  onSave: (trip: Partial<Trip>) => void;
  initialData?: Partial<Trip>;
}

const TripModal: React.FC<TripModalProps> = ({
  open,
  onClose,
  onSave,
  initialData,
}) => {
  const [formData, setFormData] = useState<Partial<Trip>>(initialData || {});
  const { data: cities } = useCities();
  const createCityMutation = useCreateCity();

  const [newCityName, setNewCityName] = useState("");
  const [newCityCountry, setNewCityCountry] = useState("");
  const [isCreatingCity, setIsCreatingCity] = useState(false);

  useEffect(() => {
    if (initialData) {
      setFormData({
        ...initialData,
        startDate: initialData.startDate?.split("T")[0],
        endDate: initialData.endDate?.split("T")[0],
      });
    } else {
      setFormData({ status: "Upcoming" }); // Default status for new trips
    }
  }, [initialData]);

  const handleChange =
    (field: keyof Trip) =>
    (event: React.ChangeEvent<HTMLInputElement | HTMLTextAreaElement>) => {
      setFormData({ ...formData, [field]: event.target.value });
    };

  const handleStatusChange = (event: SelectChangeEvent<string>) => {
    setFormData({
      ...formData,
      status: event.target.value as "Upcoming" | "Completed",
    });
  };

  const handleCityChange = (event: SelectChangeEvent<number>) => {
    const selectedCity = cities?.find(
      (city) => city.id === Number(event.target.value)
    );
    setFormData({
      ...formData,
      city: selectedCity,
      cityId: selectedCity ? selectedCity.id : undefined,
    });
  };

  const handleSave = () => {
    onSave(formData);
    onClose();
  };

  const handleCreateCity = () => {
    createCityMutation.mutate(
      { name: newCityName, country: newCityCountry },
      {
        onSuccess: (newCity) => {
          setFormData({ ...formData, city: newCity, cityId: newCity.id });
          setIsCreatingCity(false);
          setNewCityName("");
          setNewCityCountry("");
        },
      }
    );
  };

  return (
    <Modal open={open} onClose={onClose}>
      <Box
        sx={{
          position: "absolute",
          top: "50%",
          left: "50%",
          transform: "translate(-50%, -50%)",
          width: 400,
          bgcolor: "background.paper",
          boxShadow: 24,
          p: 4,
          borderRadius: 2,
        }}
      >
        <Typography variant="h6" gutterBottom>
          {initialData ? "Edit Trip" : "Create Trip"}
        </Typography>
        <TextField
          fullWidth
          label="Name"
          value={formData.name || ""}
          onChange={handleChange("name")}
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          label="Description"
          value={formData.description || ""}
          onChange={handleChange("description")}
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          label="Start Date"
          type="date"
          value={formData.startDate || ""}
          onChange={handleChange("startDate")}
          placeholder="dd/mm/yyyy"
          InputLabelProps={{ shrink: true }} // Prevents label overlap
          sx={{ mb: 2 }}
        />
        <TextField
          fullWidth
          label="End Date"
          type="date"
          value={formData.endDate || ""}
          onChange={handleChange("endDate")}
          placeholder="dd/mm/yyyy"
          InputLabelProps={{ shrink: true }} // Prevents label overlap
          sx={{ mb: 2 }}
        />
        <Typography variant="body1" sx={{ mb: 1 }}>
          Status
        </Typography>
        <Select
          fullWidth
          value={formData.status || "Upcoming"}
          onChange={handleStatusChange}
          sx={{ mb: 2 }}
        >
          <MenuItem value="Upcoming">Upcoming</MenuItem>
          <MenuItem value="Completed">Completed</MenuItem>
        </Select>
        <Typography variant="body1" sx={{ mb: 1 }}>
          Select City
        </Typography>
        <Select
          fullWidth
          value={formData.city?.id || ""}
          onChange={handleCityChange}
          displayEmpty
          sx={{ mb: 2 }}
        >
          <MenuItem value="" disabled>
            Select a city
          </MenuItem>
          {cities?.map((city) => (
            <MenuItem key={city.id} value={city.id}>
              {city.name} ({city.country})
            </MenuItem>
          ))}
        </Select>
        {!isCreatingCity && (
          <Button
            onClick={() => setIsCreatingCity(true)}
            variant="outlined"
            fullWidth
          >
            Create New City
          </Button>
        )}
        {isCreatingCity && (
          <Box sx={{ mt: 2 }}>
            <TextField
              fullWidth
              label="City Name"
              value={newCityName}
              onChange={(e) => setNewCityName(e.target.value)}
              sx={{ mb: 2 }}
            />
            <TextField
              fullWidth
              label="Country"
              value={newCityCountry}
              onChange={(e) => setNewCityCountry(e.target.value)}
              sx={{ mb: 2 }}
            />
            <Button onClick={handleCreateCity} variant="contained" fullWidth>
              Save City
            </Button>
          </Box>
        )}
        <Box sx={{ textAlign: "right", mt: 2 }}>
          <Button onClick={onClose} sx={{ mr: 1 }}>
            Cancel
          </Button>
          <Button variant="contained" onClick={handleSave}>
            Save
          </Button>
        </Box>
      </Box>
    </Modal>
  );
};

export default TripModal;
