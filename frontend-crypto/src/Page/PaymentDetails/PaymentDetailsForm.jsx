import {
  TextField,
  Button,
  CircularProgress,
  Typography,
  Box,
} from '@mui/material';
import { useForm } from 'react-hook-form';
import { useDispatch, useSelector } from 'react-redux';
import { yupResolver } from '@hookform/resolvers/yup';
import * as yup from 'yup';
import { useEffect } from 'react';

// import your action
import { addPaymentDetails } from "@/Redux/Withdrawal/Action";


const formSchema = yup.object().shape({
  accountHolderName: yup.string().required('Account holder name is required'),
  ifsc: yup.string().length(11, 'IFSC code must be 11 characters'),
  accountNumber: yup.string().required('Account number is required'),
  confirmAccountNumber: yup
    .string()
    .oneOf([yup.ref('accountNumber'), null], 'Account numbers do not match'),
  bankName: yup.string().required('Bank name is required'),
});

const PaymentDetailsForm = () => {
  const dispatch = useDispatch();
  const { auth } = useSelector((store) => store);


  const {
    register,
    handleSubmit,
    formState: { errors },
  } = useForm({
    resolver: yupResolver(formSchema),
    defaultValues: {
      accountHolderName: '',
      ifsc: '',
      accountNumber: '',
      bankName: '',
    },
  });

  const onSubmit = (data) => {
    dispatch(
      addPaymentDetails({
        paymentDetails: data,
        jwt: localStorage.getItem('jwt'),
      })
    );
    console.log('payment details form', data);
  };

  return (
    <Box px={4} py={2}>
      <form onSubmit={handleSubmit(onSubmit)} noValidate>
        <Box mb={3}>
          <TextField
            fullWidth
            label="Account Holder Name"
            {...register('accountHolderName')}
            error={!!errors.accountHolderName}
            helperText={errors.accountHolderName?.message}
            variant="outlined"
            InputProps={{ style: { padding: '16px' } }}
          />
        </Box>

        <Box mb={3}>
          <TextField
            fullWidth
            label="IFSC Code"
            {...register('ifsc')}
            error={!!errors.ifsc}
            helperText={errors.ifsc?.message}
            variant="outlined"
            placeholder="YESB0000009"
            InputProps={{ style: { padding: '16px' } }}
          />
        </Box>

        <Box mb={3}>
          <TextField
            fullWidth
            label="Account Number"
            {...register('accountNumber')}
            error={!!errors.accountNumber}
            helperText={errors.accountNumber?.message}
            variant="outlined"
            placeholder="********5602"
            InputProps={{ style: { padding: '16px' } }}
          />
        </Box>

        <Box mb={3}>
          <TextField
            fullWidth
            label="Confirm Account Number"
            {...register('confirmAccountNumber')}
            error={!!errors.confirmAccountNumber}
            helperText={errors.confirmAccountNumber?.message}
            variant="outlined"
            placeholder="Confirm Account Number"
            InputProps={{ style: { padding: '16px' } }}
          />
        </Box>

        <Box mb={4}>
          <TextField
            fullWidth
            label="Bank Name"
            {...register('bankName')}
            error={!!errors.bankName}
            helperText={errors.bankName?.message}
            variant="outlined"
            placeholder="YES Bank"
            InputProps={{ style: { padding: '16px' } }}
          />
        </Box>

        {!auth.loading ? (
          <Button
            type="submit"
            fullWidth
            variant="contained"
            sx={{ py: 2, fontWeight: 'bold' }}
          >
            SUBMIT
          </Button>
        ) : (
          <Box display="flex" justifyContent="center">
            <CircularProgress />
          </Box>
        )}
      </form>
    </Box>
  );
};

export default PaymentDetailsForm;
