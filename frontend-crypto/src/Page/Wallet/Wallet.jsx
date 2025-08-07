import React, {useEffect, useState} from 'react'
import {
  Card,
  CardHeader,
  CardContent,
  Typography,
  IconButton,
  Grid,
  Dialog,
  DialogTitle,
  DialogContent,
  Button,
  Box, Avatar,
} from "@mui/material";
import ContentCopyIcon from "@mui/icons-material/ContentCopy";
import AutorenewIcon from "@mui/icons-material/Autorenew";
import UploadIcon from "@mui/icons-material/Upload";
import DownloadIcon from "@mui/icons-material/Download";
import SwapHorizIcon from "@mui/icons-material/SwapHoriz";
import AccountBalanceWalletIcon from "@mui/icons-material/AccountBalanceWallet";
import AttachMoneyIcon from "@mui/icons-material/AttachMoney";
import ShuffleIcon from '@mui/icons-material/Shuffle';
import TopupForm from "./TopupForm";
import TransferForm from "./TransferForm";
import WithdrawForm from "./WithdrawForm";
import { useDispatch, useSelector } from "react-redux";
import { getUserWallet, getWalletTransactions } from "../../Redux/Wallet/Action";

const Wallet = () => {
  const dispatch = useDispatch();
  const { wallet } = useSelector((store) => store);
  const [openTopup, setOpenTopup] = useState(false);
  const [openWithdraw, setOpenWithdraw] = useState(false);
  const [openTransfer, setOpenTransfer] = useState(false);

  useEffect(() => {
    handleFetchUserWallet();
  },[])

  const hanldeFetchWalletTransactions = () => {
    dispatch(getWalletTransactions({ jwt: localStorage.getItem("jwt") }));
  };

  const handleFetchUserWallet = () => {
    dispatch(getUserWallet({ jwt: localStorage.getItem("jwt") }))}

  return (
    <div className='flex flex-col items-center'>
      <div className='pt-10 w-full lg:w-[60%]'>
               <Card elevation={4}>
          <CardHeader
            title={
              <Box display="flex" alignItems="center" gap={2}>
                <AccountBalanceWalletIcon fontSize="large" />
                <Box>
                  <Typography variant="h5">My Wallet</Typography>
                  <Box display="flex" alignItems="center" gap={1}>
                    <Typography variant="caption" color="text.secondary">
                      #FAVHJY
                    </Typography>
                    <IconButton
                      size="small"
                      onClick={() => copyToClipboard(wallet.userWallet?.id)}
                    >
                      <ContentCopyIcon fontSize="small" />
                    </IconButton>
                  </Box>
                </Box>
              </Box>
            }
            action={
              <IconButton onClick={handleFetchUserWallet}>
                <AutorenewIcon />
              </IconButton>
            }
          />

          <CardContent>
            {/* Balance */}
            <Box display="flex" alignItems="center" gap={1}>
              <AttachMoneyIcon />
              <Typography variant="h6" fontWeight="bold">
                {wallet.userWallet?.balance ? wallet.userWallet.balance.toFixed(2) : "0.00"} USD
              </Typography>
            </Box>

            {/* Actions */}
            <Grid container spacing={3} mt={2}>
              <Grid item>
                <Box
                  onClick={() => setOpenTopup(true)}
                  sx={{
                    cursor: "pointer",
                    p: 2,
                    width: 96,
                    height: 96,
                    borderRadius: 2,
                    boxShadow: 2,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    "&:hover": { color: "gray" },
                  }}
                >
                  <UploadIcon />
                  <Typography variant="caption" mt={1}>
                    Add Money
                  </Typography>
                </Box>
              </Grid>

              <Grid item>
                <Box
                  onClick={() => setOpenWithdraw(true)}
                  sx={{
                    cursor: "pointer",
                    p: 2,
                    width: 96,
                    height: 96,
                    borderRadius: 2,
                    boxShadow: 2,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    "&:hover": { color: "gray" },
                  }}
                >
                  <DownloadIcon />
                  <Typography variant="caption" mt={1}>
                    Withdraw
                  </Typography>
                </Box>
              </Grid>

              <Grid item>
                <Box
                  onClick={() => setOpenTransfer(true)}
                  sx={{
                    cursor: "pointer",
                    p: 2,
                    width: 96,
                    height: 96,
                    borderRadius: 2,
                    boxShadow: 2,
                    display: "flex",
                    flexDirection: "column",
                    alignItems: "center",
                    justifyContent: "center",
                    "&:hover": { color: "gray" },
                  }}
                >
                  <SwapHorizIcon />
                  <Typography variant="caption" mt={1}>
                    Transfer
                  </Typography>
                </Box>
              </Grid>
            </Grid>
          </CardContent>
        </Card>
        <Dialog open={openTopup} onClose={() => setOpenTopup(false)} maxWidth="sm" fullWidth>
          <DialogTitle>Top Up Your Wallet</DialogTitle>
          <DialogContent>
            <TopupForm />
          </DialogContent>
        </Dialog>

        {/* Withdraw Dialog */}
        <Dialog open={openWithdraw} onClose={() => setOpenWithdraw(false)} maxWidth="sm" fullWidth>
          <DialogTitle>Request Withdrawal</DialogTitle>
          <DialogContent>
            <WithdrawForm />
          </DialogContent>
        </Dialog>

        {/* Transfer Dialog */}
        <Dialog open={openTransfer} onClose={() => setOpenTransfer(false)} maxWidth="sm" fullWidth>
          <DialogTitle>Transfer To Other Wallet</DialogTitle>
          <DialogContent>
            <TransferForm />
          </DialogContent>
        </Dialog>

       <div className="py-5 pt-10">
      <div className="flex gap-2 items-center pb-5">
        <h1 className="text-2xl font-semibold">History</h1>
        <IconButton onClick={hanldeFetchWalletTransactions} size="small">
          <AutorenewIcon className="hover:text-gray-400" />
        </IconButton>
      </div>

      <div className="space-y-5">
        {[1,1,1,1,1,1,1,1].map((item, index) => (
          <Card
            key={index}
            variant="outlined"
            className="px-5 py-2 flex justify-between items-center lg:w-[50]"
          >
            <div className="flex items-center gap-5">
              <Avatar>
                <ShuffleIcon />
              </Avatar>
              <div className="space-y-1">
                <Typography variant="body1">Buy Coin</Typography>
                <Typography variant="body2" className="text-gray-500">
                  {item.date || "2023-10-01 12:00 PM"}
                </Typography>
              </div>
            </div>

            <div>
              <Typography
                variant="body1"
                className={`flex items-center ${
                  item.amount > 0 ? 'text-green-500' : 'text-red-500'
                }`}
              >
                785266 USD
              </Typography>
            </div>
          </Card>
        ))}
      </div>
    </div>

      </div>
    </div>
  )
}

export default Wallet
