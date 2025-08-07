import React from "react";
import {
  TextField,
  Button,
  Typography,
  CircularProgress,
  Box,
  Container,
  Paper,
  Link,
} from "@mui/material";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useDispatch, useSelector } from "react-redux";
import { login } from "@/Redux/Auth/Action";
import { useNavigate, Link as RouterLink } from "react-router-dom";


// Zod validation
const formSchema = z.object({
  email: z.string().email("Invalid email address"),
  password: z.string().min(8, "Password must be at least 8 characters long"),
});

const LoginForm = () => {
  const navigate = useNavigate();
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);

  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: zodResolver(formSchema),
    defaultValues: {
      email: "",
      password: "",
    },
  });

const onSubmit = (data) => {
  dispatch(login({ ...data, navigate }));
};


  return (
    <Box
      sx={{
        backgroundColor: "#f9f9f9",
        minHeight: "50vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        px: 2,
      }}
    >
      <Container maxWidth="sm">
        <Paper
          elevation={3}
          sx={{
            p: 5,
            borderRadius: 3,
            boxShadow: "0 4px 20px rgba(0,0,0,0.05)",
            backgroundColor: "#ffffff",
          }}
        >
          <Typography
            variant="h4"
            align="center"
            gutterBottom
            sx={{ fontWeight: 500, color: "#333", mb: 4 }}
          >
            Welcome Back
          </Typography>
          <form onSubmit={handleSubmit(onSubmit)}>
            <Box mb={3}>
              <TextField
                fullWidth
                label="Email Address"
                variant="outlined"
                {...register("email")}
                error={!!errors.email}
                helperText={errors.email?.message}
                sx={{
                  "& .MuiInputBase-root": {
                    backgroundColor: "#fafafa",
                    borderRadius: 2,
                  },
                }}
              />
            </Box>
            <Box mb={1}>
              <TextField
                fullWidth
                type="password"
                label="Password"
                variant="outlined"
                {...register("password")}
                error={!!errors.password}
                helperText={errors.password?.message}
                sx={{
                  "& .MuiInputBase-root": {
                    backgroundColor: "#fafafa",
                    borderRadius: 2,
                  },
                }}
              />
            </Box>

            {/* Forgot Password Link */}
            <Box textAlign="right" mb={3}>
              <Link
                component={RouterLink}
                to="/forgot-password"
                variant="body2"
                underline="hover"
                sx={{ color: "#607d8b", fontWeight: 500 }}
              >
                Forgot password?
              </Link>
            </Box>

            <Button
              type="submit"
              fullWidth
              variant="contained"
              disabled={auth.loading}
              sx={{
                backgroundColor: "#607d8b",
                color: "#fff",
                fontWeight: 600,
                py: 1.5,
                borderRadius: 2,
                "&:hover": {
                  backgroundColor: "#546e7a",
                },
              }}
            >
              {auth.loading ? (
                <CircularProgress size={24} color="inherit" />
              ) : (
                "Login"
              )}
            </Button>

          </form>
        </Paper>
      </Container>
    </Box>
  );
};

export default LoginForm;
