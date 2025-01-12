import { TextField, TextFieldProps } from "@mui/material";
import React from "react";

interface InputProps extends Omit<TextFieldProps, "variant"> {
  label: string;
}

const Input: React.FC<InputProps> = ({ label, ...props }) => {
  return (
    <TextField
      {...props}
      fullWidth
      variant="outlined" // Ensures variant is always "outlined"
      label={label}
    />
  );
};

export default Input;
