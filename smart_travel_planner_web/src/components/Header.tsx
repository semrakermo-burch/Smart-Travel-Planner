import { AppBar, Toolbar, Typography } from "@mui/material";
import React from "react";

const Header: React.FC = () => {
  return (
    <AppBar position="static" sx={{ marginBottom: 4 }}>
      <Toolbar>
        <Typography variant="h6" component="div">
          Smart Travel Planner
        </Typography>
      </Toolbar>
    </AppBar>
  );
};

export default Header;
