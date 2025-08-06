import React, { useState } from "react";
import {
  Container,
  TextField,
  Button,
  Typography,
  Paper,
  Box,
  Divider,
} from "@mui/material";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { z } from "zod";
import { useDispatch } from "react-redux";
import { sendResetPassowrdOTP } from "@/Redux/Auth/Action";
import { useNavigate } from "react-router-dom";

// Zod schema
const formSchema = z.object({
  email: z.string().email("Please enter a valid email address"),
});

const ForgotPasswordForm = () => {
  const [verificationType] = useState("EMAIL");
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
    },
  });

  const onSubmit = (data) => {
    dispatch(
      sendResetPassowrdOTP({
        sendTo: data.email,
        navigate,
        verificationType,
      })
    );
  };

  return (
    <Box
      sx={{
        backgroundColor: "#f7f7f7",
        minHeight: "50vh",
        display: "flex",
        alignItems: "center",
        justifyContent: "center",
        padding: 2,
      }}
    >
      <Container maxWidth="sm">
        <Paper
          elevation={4}
          sx={{
            p: 5,
            borderRadius: 3,
            backgroundColor: "#ffffff",
            boxShadow: "0 4px 20px rgba(0,0,0,0.05)",
          }}
        >
          <Typography
            variant="h5"
            align="center"
            fontWeight="bold"
            gutterBottom
            sx={{ color: "#333" }}
          >
            Forgot Your Password?
          </Typography>
          <Typography
            variant="body1"
            align="center"
            gutterBottom
            sx={{ color: "#666" }}
          >
            Enter your email to receive a verification code
          </Typography>

          <Divider sx={{ my: 3 }} />

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
                  backgroundColor: "#fafafa",
                  input: { fontSize: "1rem" },
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
                fontWeight: 600,
                fontSize: "1rem",
                borderRadius: 2,
              }}
            >
              Send OTP
            </Button>
          </form>
        </Paper>
      </Container>
    </Box>
  );
};

export default ForgotPasswordForm;
