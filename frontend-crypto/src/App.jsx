import { useState } from 'react'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'
import Navbar from './Page/Navbar/Navbar.jsx'
import Home from './Page/Home/Home.jsx'
function App() {

  return (
    <>
      <Navbar auth={{ user: { fullName: "Crypto" } }} />
      <Home />
    </>
  )
}

export default App
