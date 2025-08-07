import React, { useState } from "react";
import {
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  TextField,
  Typography,
  Box,
} from "@mui/material";

const AccountVerificationForm = ({ handleSubmit}) => {
  const [otp, setOtp] = useState("");
  const [open, setOpen] = useState(false);

  const handleClickOpen = () => {
    handleSendOtp("EMAIL"); // trigger OTP send
    setOpen(true);
  };

  const handleClose = () => {
    setOpen(false);
  };

  const handleOtpSubmit = () => {
    handleSubmit(otp); // send entered OTP
    setOtp("");
    setOpen(false);
  };

  const handleSendOtp = (verificationType) => {
    dispatch(
      sendVerificationOtp({
        verificationType,
        jwt: localStorage.getItem("jwt"),
      })
    );
  };


  return (
    <Box p={2}>
      <Typography variant="h6" gutterBottom>
        Email Verification
      </Typography>

      <Typography variant="body1" gutterBottom>
        Send OTP to email for verification.
      </Typography>

      <Button variant="contained" onClick={handleClickOpen}>
        Send OTP
      </Button>

      <Dialog open={open} onClose={handleClose}>
        <DialogTitle>Email Verification</DialogTitle>
        <DialogContent>
          <TextField
            autoFocus
            margin="dense"
            label="Enter 6-digit OTP"
            type="text"
            fullWidth
            value={otp}
            onChange={(e) => setOtp(e.target.value)}
          />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleClose} color="secondary">
            Cancel
          </Button>
          <Button onClick={handleOtpSubmit} color="primary">
            Submit OTP
          </Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

export default AccountVerificationForm;
