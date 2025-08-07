import { useEffect, useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Navbar from './Page/Navbar/Navbar.jsx'
import Home from './Page/Home/Home.jsx'
import { Routes, Route } from 'react-router-dom'
import StockDetails from './Page/StockDetails/StockDetails.jsx'
import SearchCoin from './Page/Search/SearchCoin.jsx'
import Profile from './Page/Profile/Profile.jsx'
import Portfolio from './Page/Portfolio/Portfolio.jsx'
import Activity from './Page/Activity/Activity.jsx'
import Wallet from './Page/Wallet/Wallet.jsx'
import Paymentdetails from './Page/PaymentDetails/Paymentdetails.jsx'
import Withdrawal from './Page/Withdrawal/Withdrawal.jsx'
import Auth from './Page/Auth/Auth.jsx'
import { useDispatch, useSelector } from 'react-redux'
import { getUser } from './Redux/Auth/Action.js'


function App() {

  const {auth} = useSelector((store) => store);
  const dispatch = useDispatch();
  console.log("auth", auth);

  useEffect(() => {dispatch(getUser( auth.jwt || localStorage.getItem("jwt")))}, [auth.jwt]);


  return (
    <>
      {auth.user? <div>
      <Navbar auth={{ user: { fullName: "Crypto" } }} />
      <Routes>
        <Route path="/" element={<Home />} />
        <Route path="/portfolio" element={<Portfolio />} />
        <Route path="/activity" element={<Activity />} />
        <Route path="/wallet" element={<Wallet />} />
        <Route path="/withdrawal" element={<Withdrawal />} />
        <Route path="/payment-details" element={<Paymentdetails />} />
        <Route path="/market/:id" element={<StockDetails />} />
        <Route path="/profile" element={<Profile />} />
        <Route path="/search" element={<SearchCoin />} />
      </Routes>
    </div>:<Auth />}
    </>
  )
}

export default App
