import React from "react";
import {
  TextField,
  Button,
  Typography,
  Paper,
  Box,
  Container,
  CircularProgress,
  Divider,
} from "@mui/material";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useNavigate } from "react-router-dom";
import { useDispatch, useSelector } from "react-redux";
import { register as registerAction } from "@/Redux/Auth/Action";

// Zod Schema
const formSchema = z.object({
  fullName: z.string().nonempty("Full name is required"),
  email: z.string().email("Invalid email address").optional(),
  password: z
    .string()
    .min(8, "Password must be at least 8 characters long")
    .optional(),
});

const SignupForm = () => {
  const { auth } = useSelector((store) => store);
  const navigate = useNavigate();
  const dispatch = useDispatch();

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      password: "",
      fullName: "",
    },
  });

  const onSubmit = (data) => {
    data.navigate = navigate;
    dispatch(registerAction(data));
  };

  return (
    <Box
      sx={{
        backgroundColor: "#f5f5f5",
        minHeight: "50vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        py: 4,
      }}
    >
      <Container maxWidth="sm">
        <Paper
          elevation={4}
          sx={{
            p: 5,
            borderRadius: 3,
            backgroundColor: "#ffffff",
            boxShadow: "0 6px 18px rgba(0,0,0,0.08)",
          }}
        >
          <Typography
            variant="h5"
            align="center"
            fontWeight="bold"
            gutterBottom
            sx={{ color: "#333" }}
          >
            Create Your Account
          </Typography>

          <Typography
            variant="body2"
            align="center"
            sx={{ color: "#666", mb: 3 }}
          >
            Sign up to access your dashboard and manage your account
          </Typography>

          <Divider sx={{ mb: 4 }} />

          <form onSubmit={handleSubmit(onSubmit)} noValidate>
            <Box mb={3}>
              <TextField
                fullWidth
                label="Full Name"
                variant="outlined"
                {...register("fullName")}
                error={!!errors.fullName}
                helperText={errors.fullName?.message}
                sx={{
                  backgroundColor: "#fafafa",
                }}
              />
            </Box>
            <Box mb={3}>
              <TextField
                fullWidth
                label="Email"
                variant="outlined"
                {...register("email")}
                error={!!errors.email}
                helperText={errors.email?.message}
                sx={{
                  backgroundColor: "#fafafa",
                }}
              />
            </Box>
            <Box mb={4}>
              <TextField
                fullWidth
                label="Password"
                type="password"
                variant="outlined"
                {...register("password")}
                error={!!errors.password}
                helperText={errors.password?.message}
                sx={{
                  backgroundColor: "#fafafa",
                }}
              />
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              sx={{
                py: 1.5,
                backgroundColor: "#4b5563",
                "&:hover": {
                  backgroundColor: "#374151",
                },
                fontWeight: "bold",
                fontSize: "1rem",
                borderRadius: 2,
              }}
              disabled={auth.loading}
            >
              {auth.loading ? (
                <CircularProgress size={24} color="inherit" />
              ) : (
                "Register"
              )}
            </Button>
          </form>
        </Paper>
      </Container>
    </Box>
  );
};

export default SignupForm;
