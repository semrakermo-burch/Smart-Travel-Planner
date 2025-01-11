import { createTheme } from "@mui/material/styles";

const theme = createTheme({
    palette: {
        primary: {
            main: "#1976d2", // A modern blue for primary buttons and links
            contrastText: "#ffffff", // White text for contrast
        },
        secondary: {
            main: "#f50057", // A vibrant pink for secondary elements
            contrastText: "#ffffff", // White text for contrast
        },
        background: {
            default: "#f5f5f5", // Light gray background for the application
            paper: "#ffffff", // White background for cards and paper elements
        },
        text: {
            primary: "#333333", // Dark gray for primary text
            secondary: "#555555", // Medium gray for secondary text
        },
    },
    typography: {
        fontFamily: "'Roboto', 'Helvetica', 'Arial', sans-serif",
        h4: {
            fontWeight: 600,
            fontSize: "2rem",
        },
        body1: {
            fontSize: "1rem",
        },
    },
});

export default theme;
