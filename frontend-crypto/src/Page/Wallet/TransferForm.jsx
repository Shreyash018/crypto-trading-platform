import { useState } from "react";
import { useDispatch } from "react-redux";

import { useSelector } from "react-redux";
import { transferMoney } from "../../Redux/Wallet/Action";


// Material UI imports
import Button from "@mui/material/Button";
import TextField from "@mui/material/TextField";
import DialogActions from "@mui/material/DialogActions";

const TransferForm = () => {
  const dispatch = useDispatch();
  const {wallet} = useSelector((store) => store);
  const [formData, setFormData] = useState({
    amount: "",
    walletId: "",
    purpose: "",
  });

  const handleChange = (e) => {
    setFormData({
      ...formData,
      [e.target.name]: e.target.value,
    });
  };

  const handleSubmit = () => {
    dispatch(
      transferMoney({
        jwt: localStorage.getItem("jwt"),
        walletId: formData.walletId,
        reqData: {
          amount: formData.amount,
          purpose: formData.purpose,
        },
      })
    );
    console.log(formData);
  };

  return (
    <div style={{ paddingTop: "2rem", display: "flex", flexDirection: "column", gap: "1.5rem" }}>
      <div>
        <h1 style={{ marginBottom: "0.5rem" }}>Enter Amount</h1>
        <TextField
          name="amount"
          onChange={handleChange}
          value={formData.amount}
          placeholder="$9999"
          fullWidth
          variant="outlined"
          InputProps={{ style: { padding: "20px" } }}
        />
      </div>

      <div>
        <h1 style={{ marginBottom: "0.5rem" }}>Enter Wallet Id</h1>
        <TextField
          name="walletId"
          onChange={handleChange}
          value={formData.walletId}
          placeholder="#ADFE34456"
          fullWidth
          variant="outlined"
          InputProps={{ style: { padding: "20px" } }}
        />
      </div>

      <div>
        <h1 style={{ marginBottom: "0.5rem" }}>Purpose</h1>
        <TextField
          name="purpose"
          onChange={handleChange}
          value={formData.purpose}
          placeholder="gift for your friend..."
          fullWidth
          variant="outlined"
          InputProps={{ style: { padding: "20px" } }}
        />
      </div>

      <DialogActions>
        <Button
          onClick={handleSubmit}
          variant="contained"
          fullWidth
          style={{ padding: "20px", fontSize: "18px" }}
        >
          Send
        </Button>
      </DialogActions>
    </div>
  );
};

export default TransferForm;
