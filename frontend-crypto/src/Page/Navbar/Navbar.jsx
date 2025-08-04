import React from "react";
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Avatar,
  Button,
  Box,
} from "@mui/material";
import MenuIcon from "@mui/icons-material/Menu";
import SearchIcon from "@mui/icons-material/Search";
import PersonIcon from "@mui/icons-material/Person";
import { useNavigate } from "react-router-dom";


const Navbar = ({ auth }) => {
  const navigate = useNavigate();

  return (
    <AppBar
      position="sticky"
      elevation={1}
      color="default"
      className="bg-white shadow-md z-120"
    >
      <Toolbar className="flex justify-between">
        {/* Left: Logo */}
        <Box className="flex items-center gap-2 cursor-pointer" onClick={() => navigate("/")}>
          <Typography variant="h6" className="font-bold text-orange-600">
           Crypto 
          </Typography>
        </Box>

        {/* Center: Links */}
        <Box className=" md:flex gap-4">
          <Button onClick={() => navigate("/")} color="inherit">
            Home
          </Button>
          <Button onClick={() => navigate("/Portfolio")} color="inherit">
           Portfolio
          </Button>
          <Button onClick={() => navigate("/Wallet")} color="inherit">
           Wallet
          </Button>
          <Button onClick={() => navigate("/Withdrawal")} color="inherit">
            Withdrawal
          </Button>
          <Button onClick={() => navigate("/Activity")} color="inherit">
            Activity
          </Button>
          <Button onClick={() => navigate("/Logout")} color="inherit">
            Logout
          </Button>
        </Box>

        {/* Right: Search + Avatar */}
        <Box className="flex items-center gap-3">
          <IconButton onClick={() => navigate("/search")} color="inherit">
            <SearchIcon />
          </IconButton>
          <IconButton onClick={() => navigate("/profile")}>
            {auth?.user ? (
              <Avatar>{auth.user.fullName[0].toUpperCase()}</Avatar>
            ) : (
              <Avatar>
                <PersonIcon />
              </Avatar>
            )}
          </IconButton>
        </Box>
      </Toolbar>
    </AppBar>
  );
};

export default Navbar;
