import { useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { useNavigate } from "react-router-dom";

// MUI Components
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";

import { maskAccountNumber } from '@/Page/Util/maskAccountNumber';
import "./WithdrawForm.css";

const WithdrawForm = () => {
  const dispatch = useDispatch();
  const [amount, setAmount] = useState();
  const { wallet, withdrawal } = useSelector((store) => store);
  const navigate = useNavigate();

  const handleChange = (e) => {
    let value = e.target.value;
    if (value.toString().length < 6) {
      setAmount(value);
    }
  };

  const handleSubmit = () => {
    dispatch(withdrawalRequest({ jwt: localStorage.getItem("jwt"), amount }));
  };

  if (!withdrawal.paymentDetails) {
    return (
      <div
        style={{
          height: "20rem",
          display: "flex",
          flexDirection: "column",
          gap: "1.25rem",
          justifyContent: "center",
          alignItems: "center",
        }}
      >
        <p style={{ fontSize: "1.5rem", fontWeight: "bold" }}>
          Add payment method
        </p>
        <Button variant="contained" onClick={() => navigate("/payment-details")}>
          Add Payment Details
        </Button>
      </div>
    );
  }

  return (
    <div style={{ paddingTop: "2.5rem", display: "flex", flexDirection: "column", gap: "1.25rem" }}>
      <div
        style={{
          display: "flex",
          justifyContent: "space-between",
          alignItems: "center",
          backgroundColor: "#1e293b",
          padding: "1rem",
          borderRadius: "0.5rem",
          color: "white",
          fontWeight: "bold",
          fontSize: "1.25rem",
        }}
      >
        <p>Available balance</p>
        <p>${wallet.userWallet?.balance}</p>
      </div>

      <div style={{ textAlign: "center" }}>
        <h1>Enter withdrawal amount</h1>
        <div style={{ display: "flex", justifyContent: "center" }}>
          <TextField
            onChange={handleChange}
            value={amount}
            variant="outlined"
            placeholder="$9999"
            type="number"
            inputProps={{
              style: {
                fontSize: "1.5rem",
                textAlign: "center",
                padding: "1.75rem 0",
              },
            }}
          />
        </div>
      </div>

      <div>
        <p style={{ paddingBottom: "0.5rem" }}>Transfer to</p>
        <div
          style={{
            display: "flex",
            alignItems: "center",
            gap: "1.25rem",
            border: "1px solid #ccc",
            padding: "0.5rem 1rem",
            borderRadius: "0.5rem",
          }}
        >
          <img
            src="https://cdn.pixabay.com/photo/2020/02/18/11/03/bank-4859142_1280.png"
            alt="bank"
            style={{ height: "2rem", width: "2rem" }}
          />
          <div>
            <p style={{ fontWeight: "bold", fontSize: "1.25rem" }}>
              {withdrawal.paymentDetails?.bankName}
            </p>
            <p style={{ fontSize: "0.75rem" }}>
              {maskAccountNumber(withdrawal.paymentDetails?.accountNumber)}
            </p>
          </div>
        </div>
      </div>

      <DialogActions style={{ width: "100%", padding: 0 }}>
        <Button
          onClick={handleSubmit}
          variant="contained"
          fullWidth
          style={{ padding: "1.75rem", fontSize: "1.25rem" }}
        >
          Withdraw {amount && <span style={{ marginLeft: "1rem" }}>${amount}</span>}
        </Button>
      </DialogActions>
    </div>
  );
};

export default WithdrawForm;
