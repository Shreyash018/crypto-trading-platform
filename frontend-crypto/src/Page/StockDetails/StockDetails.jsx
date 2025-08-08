import {
  Box,
  Button,
  Avatar,
  Typography,
  Dialog,
  DialogTitle,
  DialogContent,
  DialogActions,
  IconButton,
  CircularProgress,
  Backdrop,
} from "@mui/material";
import {
  Bookmark,
  BookmarkBorder,
  FiberManualRecord as DotIcon,
} from "@mui/icons-material";
import StockChart from "./StockChart";
import TradingForm from "./Trading";
import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import { useDispatch, useSelector } from "react-redux";
import { fetchCoinDetails } from "@/Redux/Coin/Action";
import { grey } from "@mui/material/colors";

const StockDetails = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { coin, auth } = useSelector((store) => store);
   const [openTradeDialog, setOpenTradeDialog] = useState(false);


  useEffect(() => {
    dispatch(
      fetchCoinDetails({
        coinId: id,
        jwt: auth.jwt || localStorage.getItem("jwt"),
      })
    );
  }, [id]);


  return (
    <Box sx={{ p: 3, mt: 2 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center">
        {/* Left: Coin Info */}
        <Box display="flex" gap={2} alignItems="center">
          <Avatar src={coin.coinDetails?.image.large}  />
          <Box>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography variant="h6">{coin.coinDetails?.symbol}</Typography>
              <DotIcon fontSize="small" sx={{ color: "gray" }} />
              <Typography variant="body2" color="text.secondary">
                {coin.coinDetails?.name}
              </Typography>
            </Box>
            <Box display="flex" alignItems="baseline" gap={1}>
              <Typography variant="h5" fontWeight="bold">
                ${coin.coinDetails?.market_data.current_price.usd}
              </Typography>
              <Typography
                variant="body2"
                color={coin.coinDetails?.market_data.market_cap_change_24h < 0 ? "error.main" : "success.main"}
              >
                {coin.coinDetails?.market_data.market_cap_change_24h} ({ coin.coinDetails?.market_data.market_cap_change_percentage_24h}%)
              </Typography>
            </Box>
          </Box>
        </Box>

        {/* Right: Actions */}
        <Box display="flex" alignItems="center" gap={2}>
          
          <Button variant="contained" sx={{
               backgroundColor: grey[800], // darker gray
                 color: '#fff',
                '&:hover': {
                 backgroundColor: grey[900], // even darker on hover
                 },
          }}
            onClick={() => setOpenTradeDialog(true)}>
            TRADE
          </Button>
        </Box>
      </Box>

      {/* Dialog */}
      <Dialog open={openTradeDialog} maxWidth="sm" fullWidth>
        <DialogTitle textAlign="center" sx={{ pt: 3 }}>
          How much do you want to spend?
        </DialogTitle>
        <DialogContent>
          <TradingForm />
        </DialogContent>
        <DialogActions>
          <Button color="secondary" onClick={() => setOpenTradeDialog(false)}>
            Cancel
          </Button>
        </DialogActions>
      </Dialog>

      {/* Chart */}
      <Box mt={5}>
        <StockChart coinId={id} />
      </Box>
    </Box>
  );
};

export default StockDetails;
