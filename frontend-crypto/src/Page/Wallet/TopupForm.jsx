import { useState } from "react";
import {
  TextField,
  FormControl,
  FormLabel,
  RadioGroup,
  FormControlLabel,
  Radio,
  Button,
  CircularProgress,
  Box,
  Paper,
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { paymentHandler } from "../../Redux/Wallet/Action";

const TopupForm = () => {
  const [amount, setAmount] = useState("");
  const [paymentMethod, setPaymentMethod] = useState("RAZORPAY");
  const { wallet } = useSelector((store) => store);
  const dispatch = useDispatch();

  const handleChange = (e) => {
    setAmount(e.target.value);
  };

  const handleSubmit = () => {
    dispatch(
      paymentHandler({
        jwt: localStorage.getItem("jwt"),
        paymentMethod,
        amount,
      })
    );
    console.log(amount, paymentMethod);
  };

  return (
    <Box pt={4} display="flex" flexDirection="column" gap={4}>
      {/* Amount Input */}
      <Box>
        <FormLabel>Enter Amount</FormLabel>
        <TextField
          fullWidth
          variant="outlined"
          placeholder="$9999"
          value={amount}
          onChange={handleChange}
          InputProps={{ sx: { py: 1.5, fontSize: "1.1rem" } }}
        />
      </Box>

      {/* Payment Method Radio */}
      <FormControl component="fieldset">
        <FormLabel>Select Payment Method</FormLabel>
        <RadioGroup
          row
          value={paymentMethod}
          onChange={(e) => setPaymentMethod(e.target.value)}
        >
          <FormControlLabel
            value="RAZORPAY"
            control={<Radio />}
            label={
              <Paper elevation={2} sx={{ px: 2, py: 1, width: 130 }}>
                <img
                  src="https://upload.wikimedia.org/wikipedia/commons/thumb/8/89/Razorpay_logo.svg/1200px-Razorpay_logo.svg.png"
                  alt="Razorpay"
                  width="100%"
                />
              </Paper>
            }
          />
        </RadioGroup>
      </FormControl>

      {/* Submit Button */}
      {wallet.loading ? (
        <Box display="flex" justifyContent="center">
          <CircularProgress />
        </Box>
      ) : (
        <Button
          fullWidth
          variant="contained"
          color="primary"
          onClick={handleSubmit}
          sx={{ py: 2, fontSize: "1rem" }}
        >
          Submit
        </Button>
      )}
    </Box>
  );
};

export default TopupForm;


