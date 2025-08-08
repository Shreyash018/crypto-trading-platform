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
} from '@mui/material';
import { maskAccountNumber } from '@/Page/Util/maskAccountNumber';
import PaymentDetailsForm from './PaymentDetailsForm';
import { useState } from 'react';
import { useSelector } from "react-redux";
import { useEffect } from 'react';
import {getPaymentDetails} from '@/Redux/Withdrawal/Action';
import { useDispatch } from 'react-redux';

function PaymentDetails() {
  const [open, setOpen] = useState(false);
  const { withdrawal } = useSelector((store) => store);
  const handleClickOpen = () => setOpen(true);
  const handleClose = () => setOpen(false);
  const dispatch = useDispatch();

  useEffect(() => {
    dispatch(getPaymentDetails({ jwt: localStorage.getItem("jwt") }));
  }, []);


  return (
    <div style={{ paddingLeft: '5rem', paddingRight: '5rem' }}>
      <Typography variant="h4" fontWeight="bold" gutterBottom sx={{ py: 4 }}>
        Payment Details
      </Typography>

      {withdrawal.paymentDetails ? (
        <Card sx={{ mb: 4 }}>
          <CardHeader
            title={withdrawal.paymentDetails.bankName.toUpperCase()}
            subheader={
              'A/C No: ' +
              maskAccountNumber(withdrawal.paymentDetails.accountNumber)
            }
          />
          <CardContent>
            <div style={{ display: 'flex', alignItems: 'center', marginBottom: 8 }}>
              <Typography sx={{ width: 120 }}>A/C Holder</Typography>
              <Typography color="text.secondary">
                : {withdrawal.paymentDetails.accountHolderName}
              </Typography>
            </div>
            <div style={{ display: 'flex', alignItems: 'center' }}>
              <Typography sx={{ width: 120 }}>IFSC</Typography>
              <Typography color="text.secondary">
                : {withdrawal.paymentDetails.ifsc.toUpperCase()}
              </Typography>
            </div>
          </CardContent>
        </Card>
      ) : (
        <>
          <Button variant="contained" sx={{ py: 2 }} onClick={handleClickOpen}>
            Add Payment Details
          </Button>
          <Dialog open={open} onClose={handleClose}>
            <DialogTitle>Payment Details</DialogTitle>
            <DialogContent sx={{ pt: 2 }}>
              <PaymentDetailsForm />
            </DialogContent>
            <DialogActions>
              <Button onClick={handleClose}>Close</Button>
            </DialogActions>
          </Dialog>
        </>
      )}
    </div>
  );
}

export default PaymentDetails;
