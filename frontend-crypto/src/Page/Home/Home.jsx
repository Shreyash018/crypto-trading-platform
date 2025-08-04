import React, {useState} from 'react'
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Avatar,
  Button,
  Box,
} from "@mui/material";
//import AssetTable from './AssetTable';
import StockChart from '../StockDetails/StockChart';

const Home = () => {
      const [category, setCategory] = useState("all");
  return (
    <div className='relative'>
        <div className='lg:flex'>
            <div className='lg:w-1/2 lg:border-r'>
            <div className='p-3 flex items-center gap-4'>
                <Button
              variant={category == "all" ? "default" : "outline"}
              onClick={() => setCategory("all")}
              className="rounded-full"
            >
              All
            </Button>
            <Button
              variant={category == "top50" ? "default" : "outline"}
              onClick={() => setCategory("top50")}
              className="rounded-full"
            >
              Top 50
            </Button>
            </div>
          
            </div>
            <div className="w-full lg:w-[50%] p-5">
                <StockChart />
            </div>
        </div>
    </div>
  )
}

export default Home
