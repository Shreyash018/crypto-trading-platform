import {
  Card,
  CardContent,
  CardHeader,
  Typography,
  Button,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  Grid,
  Box,
  Divider,
} from '@mui/material';
import { useDispatch, useSelector } from 'react-redux';
import { useState } from 'react';
import AccountVarificationForm from './AccountVarificationForm';
import { enableTwoStepAuthentication, verifyOtp } from '@/Redux/Auth/Action';

const Profile = () => {
  const { auth } = useSelector((store) => store);
  const dispatch = useDispatch();

  const [openTwoStepDialog, setOpenTwoStepDialog] = useState(false);
  const [openVerifyDialog, setOpenVerifyDialog] = useState(false);

  const handleEnableTwoStepVerification = (otp) => {
    dispatch(enableTwoStepAuthentication({ jwt: localStorage.getItem('jwt'), otp }));
    setOpenTwoStepDialog(false);
  };

  const handleVerifyOtp = (otp) => {
    dispatch(verifyOtp({ jwt: localStorage.getItem('jwt'), otp }));
    setOpenVerifyDialog(false);
  };

  return (
    <Box
      maxWidth="1100px"
      mx="auto"
      pt={6}
      pb={10}
      px={2}
      sx={{ backgroundColor: '#f4f6f8', minHeight: '100vh' }}
    >
      {/* User Info */}
      <StyledCard title="Your Information">
        <Grid container spacing={3}>
          <Grid item xs={12} md={6}>
            <InfoItem label="Email" value="ababab@gmail.com" />
            <InfoItem label="Full Name" value="Crypto" />
            <InfoItem label="Date Of Birth" value="25/09/2000" />
            <InfoItem label="Nationality" value="Indian" />
          </Grid>
          <Grid item xs={12} md={6}>
            <InfoItem label="Address" value="Crypto" />
            <InfoItem label="City" value="Mumbai" />
            <InfoItem label="Postcode" value="345020" />
            <InfoItem label="Country" value="India" />
          </Grid>
        </Grid>
      </StyledCard>

      {/* Two-Step Verification */}
      <StyledCard title="2 Step Verification">
        <Typography variant="body2" mb={2} color="text.secondary">
          Add an extra layer of security by enabling two-step verification.
        </Typography>
        <Button
          variant="contained"
          size="large"
          sx={buttonStyle}
          onClick={() => setOpenTwoStepDialog(true)}
        >
          Enable Two Step Verification
        </Button>
      </StyledCard>

      {/* Change Password & Account Status */}
      <Grid container spacing={3}>
        <Grid item xs={12} md={6}>
          <StyledCard title="Change Password">
            <InfoItem label="Email" value="shreasd" />
            <Box mt={2}>
              <Typography variant="body2" color="text.secondary" sx={{ mb: 1 }}>
                Password
              </Typography>
              <Button variant="outlined" fullWidth sx={outlinedButtonStyle}>
                Change Password
              </Button>
            </Box>
          </StyledCard>
        </Grid>

        <Grid item xs={12} md={6}>
          <StyledCard title="Account Status">
            <InfoItem label="Email" value="ababaab" />
            <InfoItem label="Mobile" value="+918987667899" />
            <Box mt={2}>
              <Button
                variant="contained"
                fullWidth
                sx={buttonStyle}
                onClick={() => setOpenVerifyDialog(true)}
              >
                Verify Account
              </Button>
            </Box>
          </StyledCard>
        </Grid>
      </Grid>

      {/* Dialogs */}
      <Dialog open={openTwoStepDialog} onClose={() => setOpenTwoStepDialog(false)}>
        <DialogTitle textAlign="center">Two Step Verification</DialogTitle>
        <DialogContent sx={{ pt: 2 }}>
          <AccountVarificationForm handleSubmit={handleEnableTwoStepVerification} />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenTwoStepDialog(false)}>Close</Button>
        </DialogActions>
      </Dialog>

      <Dialog open={openVerifyDialog} onClose={() => setOpenVerifyDialog(false)}>
        <DialogTitle textAlign="center">Verify Your Account</DialogTitle>
        <DialogContent sx={{ pt: 2 }}>
          <AccountVarificationForm handleSubmit={handleVerifyOtp} />
        </DialogContent>
        <DialogActions>
          <Button onClick={() => setOpenVerifyDialog(false)}>Close</Button>
        </DialogActions>
      </Dialog>
    </Box>
  );
};

// Styled reusable card component
const StyledCard = ({ title, children }) => (
  <Card
    elevation={2}
    sx={{
      mb: 4,
      borderRadius: 3,
      background: 'linear-gradient(to right, #f7f7f7, #e0e0e0)',
      border: '1px solid #dcdcdc',
    }}
  >
    <CardHeader
      title={<Typography variant="h6" fontWeight={600}>{title}</Typography>}
      sx={{ backgroundColor: '#f0f0f0', py: 2 }}
    />
    <Divider />
    <CardContent>{children}</CardContent>
  </Card>
);

// Info row
const InfoItem = ({ label, value }) => (
  <Box display="flex" justifyContent="space-between" py={1}>
    <Typography fontWeight={500}>{label}:</Typography>
    <Typography color="text.secondary">{value}</Typography>
  </Box>
);

// Button styles
const buttonStyle = {
  backgroundColor: '#4a4a4a',
  color: '#fff',
  borderRadius: '8px',
  '&:hover': {
    backgroundColor: '#2f2f2f',
  },
};

const outlinedButtonStyle = {
  borderColor: '#aaa',
  color: '#333',
  borderRadius: '8px',
  '&:hover': {
    backgroundColor: '#f0f0f0',
  },
};

export default Profile;
