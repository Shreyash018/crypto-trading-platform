import React, { useState } from "react";
import {
  Box,
  Button,
  Typography,
  Card,
  Fade,
  Slide,
  useTheme,
} from "@mui/material";
import { useNavigate, useLocation } from "react-router-dom";
import { useSelector } from "react-redux";

import LoginForm from "./login/login";
import SignupForm from "./signup/SignupForm";
import ForgotPasswordForm from "./ForgotPassword";

const Auth = () => {
  const navigate = useNavigate();
  const location = useLocation();
  const { auth } = useSelector((store) => store);
  const theme = useTheme();

  const [animate, setAnimate] = useState(false);

  const handleNavigation = (path) => {
    navigate(path);
  };

  return (
    <Box
      sx={{
        minHeight: "50vh",
        backgroundColor: "#f0f2f5",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        p: 2,
      }}
    >
      <Fade in timeout={600}>
        <Card
          elevation={6}
          sx={{
            width: 520,
            minHeight: 300,
            borderRadius: 3,
            p: 4,
            backgroundColor: "#ffffff",
            boxShadow: "0px 8px 20px rgba(0, 0, 0, 0.08)",
            display: "flex",
            flexDirection: "column",
            justifyContent: "flex-start",
          }}
        >
          {/* Header */}
          <Typography
            variant="h4"
            align="center"
            sx={{ fontWeight: 1000, mb: 1.5, color: "#3f3f3f" }} // Reduced mb
          >
           CryptoBOT
          </Typography>

          {/* Conditional Forms */}
          {location.pathname === "/signup" ? (
            <Slide in direction="down" timeout={400}>
              <Box>
                <Box mb={1}>
                  <SignupForm />
                </Box>
                <Box
                  mt={1} // Reduced spacing
                  display="flex"
                  justifyContent="center"
                  alignItems="center"
                >
                  <Typography variant="body2" mr={1}>
                    Already have an account?
                  </Typography>
                  <Button onClick={() => handleNavigation("/signin")}>
                    Sign In
                  </Button>
                </Box>
              </Box>
            </Slide>
          ) : location.pathname === "/forgot-password" ? (
            <Slide in direction="left" timeout={400}>
              <Box>
                <Box mb={1}>
                  <ForgotPasswordForm />
                </Box>
                <Box
                  mt={1}
                  display="flex"
                  justifyContent="center"
                  alignItems="center"
                >
                  <Typography variant="body2" mr={1}>
                    Back to Login?
                  </Typography>
                  <Button onClick={() => navigate("/signin")}>
                    Sign In
                  </Button>
                </Box>
              </Box>
            </Slide>
          ) : (
            <Slide in direction="up" timeout={400}>
              <Box>
                <Box mb={1}>
                  <LoginForm />
                </Box>
                <Box
                  mt={1}
                  display="flex"
                  justifyContent="center"
                  alignItems="center"
                >
                  <Typography variant="body2" mr={1}>
                    Don't have an account?
                  </Typography>
                  <Button onClick={() => handleNavigation("/signup")}>
                    Sign Up
                  </Button>
                </Box>
                <Button
                  onClick={() => navigate("/forgot-password")}
                  variant="outlined"
                  fullWidth
                  sx={{ mt: 1, py: 1.2 }} // Slightly reduced margin-top
                >
                  Forgot Password?
                </Button>
              </Box>
            </Slide>
          )}
        </Card>
      </Fade>
    </Box>
  );
};

export default Auth;
