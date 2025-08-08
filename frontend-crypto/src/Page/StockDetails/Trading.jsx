import { useEffect, useState } from "react";
import {
  Box,
  Button,
  Avatar,
  Typography,
  TextField,
  Divider,
  Grid,
} from "@mui/material";
import { useDispatch, useSelector } from "react-redux";
import { getAssetDetails } from "@/Redux/Assets/Action";
import { payOrder } from "@/Redux/Order/Action";
import { DollarSign } from "lucide-react";

const TradingForm = () => {
  const dispatch = useDispatch();
  const { coin, asset, wallet } = useSelector((store) => store);

  const [quantity, setQuantity] = useState(0);
  const [amount, setAmount] = useState(0);
  const [orderType, setOrderType] = useState("BUY");
  

  const handleAmountChange = (e) => {
    const newAmount = parseFloat(e.target.value);
    setAmount(newAmount);
    const price = coin.coinDetails?.market_data.current_price.usd || 1;
    const calculatedQuantity = newAmount / price;
    setQuantity(Number.isFinite(calculatedQuantity) ? calculatedQuantity.toFixed(5) : 0);
  };

  const handleBuyCrypto = () => {
    dispatch(
      payOrder({
        jwt: localStorage.getItem("jwt"),
        amount,
        orderData: {
          coinId: coin.coinDetails?.id,
          quantity,
          orderType,
        },
      })
    );
  };

  useEffect(() => {
    dispatch(
      getAssetDetails({
        coinId: coin.coinDetails?.id,
        jwt: localStorage.getItem("jwt"),
      })
    );
  }, [coin.coinDetails?.id]);

  const coinPrice = coin.coinDetails?.market_data?.current_price?.usd || 0;
  const coinName = coin.coinDetails?.name || "Coin";
  const coinSymbol = coin.coinDetails?.symbol?.toUpperCase() || "SYM";
  const marketChange = coin.coinDetails?.market_data?.market_cap_change_24h || 0;
  const marketChangePercentage = coin.coinDetails?.market_data?.market_cap_change_percentage_24h || 0;
  const walletBalance = wallet.userWallet?.balance || 0;
  const userQuantity = asset.assetDetails?.quantity || 0;

  const isInsufficientSell =
    orderType === "SELL" && amount > userQuantity * coinPrice;

  const isInsufficientBuy =
    orderType === "BUY" && amount > walletBalance;

  const isDisabled =
    quantity <= 0 || amount <= 0 || (orderType === "SELL" ? isInsufficientSell : isInsufficientBuy);

  return (
    <Box sx={{ p: 3 }}>
      {/* Input Section */}
      <Grid container spacing={2} alignItems="center">
        <Grid item xs={12} md={7}>
          <TextField
            label="Enter Amount (USD)"
            type="number"
            fullWidth
            onChange={handleAmountChange}
            variant="outlined"
          />
        </Grid>
        <Grid item xs={12} md={5}>
          <Box
            sx={{
              border: "1px solid #ccc",
              borderRadius: 1,
              padding: 2,
              textAlign: "center",
            }}
          >
            <Typography variant="h6">{quantity}</Typography>
            <Typography variant="caption" color="text.secondary">
              {orderType === "BUY" ? "Buying Quantity" : "Selling Quantity"}
            </Typography>
          </Box>
        </Grid>
      </Grid>

      {/* Error Message */}
      {isInsufficientSell && (
        <Typography color="error" sx={{ mt: 2, textAlign: "center" }}>
          Insufficient quantity to sell
        </Typography>
      )}
      {isInsufficientBuy && (
        <Typography color="error" sx={{ mt: 2, textAlign: "center" }}>
          Insufficient wallet balance to buy
        </Typography>
      )}

      {/* Coin Details */}
      <Box display="flex" alignItems="center" gap={2} mt={4}>
        <Avatar src={coin.coinDetails?.image?.large} alt={coinName} />
        <Box>
          <Typography variant="subtitle1">{coinSymbol}</Typography>
          <Typography variant="body2" color="text.secondary">
            {coinName}
          </Typography>
          <Typography variant="h6" fontWeight="bold">
            ${coinPrice}
          </Typography>
          <Typography
            variant="body2"
            color={marketChange < 0 ? "error" : "success.main"}
          >
            {marketChange} ({marketChangePercentage}%)
          </Typography>
        </Box>
      </Box>

      <Divider sx={{ my: 3 }} />

      {/* Order Info */}
      <Box display="flex" justifyContent="space-between" mb={2}>
        <Typography>Order Type</Typography>
        <Typography>Market Order</Typography>
      </Box>
      <Box display="flex" justifyContent="space-between" mb={2}>
        <Typography>
          {orderType === "BUY" ? "Available Cash" : "Available Quantity"}
        </Typography>
        <Typography fontWeight="bold">
          {orderType === "BUY" ? (
            <span>
              {walletBalance}
              <DollarSign size={15} style={{ verticalAlign: "middle" }} /> 
            </span>
          ) : (
            userQuantity
          )}
        </Typography>
      </Box>

      {/* Action Buttons */}
      <Button
        fullWidth
        variant="contained"
        color={orderType === "BUY" ? "primary" : "error"}
        sx={{ py: 1.5, fontWeight: "bold" }}
        onClick={handleBuyCrypto}
        disabled={isDisabled}
      >
        {orderType}
      </Button>
      <Button
        fullWidth
        variant="text"
        onClick={() => setOrderType(orderType === "BUY" ? "SELL" : "BUY")}
        sx={{ mt: 2, fontSize: "1rem" }}
      >
        {orderType === "BUY" ? "Or Sell" : "Or Buy"}
      </Button>
    </Box>
  );
};

export default TradingForm;
