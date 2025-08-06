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
// import { existInWatchlist } from "@/Page/Util/existInWatchlist";
import { addItemToWatchlist, getUserWatchlist } from "@/Redux/Watchlist/Action";
import { getUserWallet } from "@/Redux/Wallet/Action";
import { grey } from "@mui/material/colors";

const StockDetails = () => {
  const { id } = useParams();
  const dispatch = useDispatch();
  const { coin, watchlist, auth } = useSelector((store) => store);

  const [openDialog, setOpenDialog] = useState(false);

  useEffect(() => {
    dispatch(
      fetchCoinDetails({
        coinId: id,
        jwt: auth.jwt || localStorage.getItem("jwt"),
      })
    );
  }, [id]);

  useEffect(() => {
    dispatch(getUserWatchlist());
    dispatch(getUserWallet(localStorage.getItem("jwt")));
  }, []);

  const handleAddToWatchlist = () => {
    dispatch(addItemToWatchlist(coin.coinDetails?.id));
  };

  const handleDialogOpen = () => setOpenDialog(true);
  const handleDialogClose = () => setOpenDialog(false);

  // const isInWatchlist = existInWatchlist();

  if (coin.loading) {
    return (
      <Backdrop open sx={{ zIndex: 9999 }}>
        <CircularProgress color="inherit" />
      </Backdrop>
    );
  }

  const {
    image,
    name,
    symbol,
    market_data = {},
  } = coin.coinDetails || {};

  const price = market_data.current_price?.usd || 0;
  const change = market_data.market_cap_change_24h || 0;
  const changePercent = market_data.market_cap_change_percentage_24h || 0;

  return (
    <Box sx={{ p: 3, mt: 2 }}>
      <Box display="flex" justifyContent="space-between" alignItems="center">
        {/* Left: Coin Info */}
        <Box display="flex" gap={2} alignItems="center">
          <Avatar src={image?.large} alt={name} />
          <Box>
            <Box display="flex" alignItems="center" gap={1}>
              <Typography variant="h6">{symbol?.toUpperCase()}</Typography>
              <DotIcon fontSize="small" sx={{ color: "gray" }} />
              <Typography variant="body2" color="text.secondary">
                {name}
              </Typography>
            </Box>
            <Box display="flex" alignItems="baseline" gap={1}>
              <Typography variant="h5" fontWeight="bold">
                ${price}
              </Typography>
              <Typography
                variant="body2"
                color={change < 0 ? "error.main" : "success.main"}
              >
                {change} ({changePercent}%)
              </Typography>
            </Box>
          </Box>
        </Box>

        {/* Right: Actions */}
        <Box display="flex" alignItems="center" gap={2}>
          <IconButton onClick={handleAddToWatchlist} size="large">
            {/* {isInWatchlist ? (
              <Bookmark fontSize="large" />
            ) : (
              <BookmarkBorder fontSize="large" />
            )} */}
          </IconButton>
          <Button variant="contained" sx={{
    backgroundColor: grey[800], // darker gray
    color: '#fff',
    '&:hover': {
      backgroundColor: grey[900], // even darker on hover
    },
  }} onClick={handleDialogOpen}>
            TRADE
          </Button>
        </Box>
      </Box>

      {/* Dialog */}
      <Dialog open={openDialog} onClose={handleDialogClose} maxWidth="sm" fullWidth>
        <DialogTitle textAlign="center" sx={{ pt: 3 }}>
          How much do you want to spend?
        </DialogTitle>
        <DialogContent>
          <TradingForm />
        </DialogContent>
        <DialogActions>
          <Button onClick={handleDialogClose} color="secondary">
            Cancel
          </Button>
        </DialogActions>
      </Dialog>

      {/* Chart */}
      <Box mt={5}>
        <StockChart coinId={coin.coinDetails?.id} />
      </Box>
    </Box>
  );
};

export default StockDetails;
