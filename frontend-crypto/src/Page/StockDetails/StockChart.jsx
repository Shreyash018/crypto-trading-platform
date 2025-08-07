import React, {useState, useEffect} from 'react'
import { useDispatch, useSelector } from "react-redux";
import ReactApexChart from 'react-apexcharts';
import {
  AppBar,
  Toolbar,
  IconButton,
  Typography,
  Avatar,
  Button,
  Box,
} from "@mui/material";

import { fetchMarketChart } from '../../Redux/Coin/Action';



const timeSeries = [
  {
    keyword: "DIGITAL_CURRENCY_DAILY",
    key: "Time Series (Daily)",
    lable: "1 Day",
    value: 1,
  },
  {
    keyword: "DIGITAL_CURRENCY_WEEKLY",
    key: "Weekly Time Series",
    lable: "1 Week",
    value: 7,
  },
  {
    keyword: "DIGITAL_CURRENCY_MONTHLY",
    key: "Monthly Time Series",
    lable: "1 Month",
    value: 30,
  },
  {
    keyword: "DIGITAL_CURRENCY_MONTHLY_3",
    key: "3 Month Time Series",
    lable: "3 Month",
    value: 90,
  },
  {
    keyword: "DIGITAL_CURRENCY_MONTHLY_6",
    key: "6 Month Time Series",
    lable: "6 Month",
    value: 180,
  },
  {
    keyword: "DIGITAL_CURRENCY_YEARLY",
    key: "Yearly Time Series",
    lable: "1 year",
    value: 365,
  },
];

const StockChart = ({coinId}) => {

  const dispatch = useDispatch();
  const {coin} = useSelector((store) => store);
  const [activeLable, setActiveLable] = useState(timeSeries[0]);
 
  

    const series = [
    {
      data: coin.marketChart.data,
    },
  ];

  const [options] = useState({
    chart: {
      id: "area-datetime",
      type: "area",
      height: 350,
      zoom: {
        autoScaleYaxis: true,
      },
    },
    annotations: {
      // your annotations
    },
    dataLabels: {
      enabled: false,
    },

    xaxis: {
      type: "datetime",
      //   min: new Date('01 Dec 2023').getTime(),
      tickAmount: 6,
    },
    colors: ["#758AA2"], // Line color
    markers: {
      colors: ["#fff"], // Dot color
      strokeColors: "#fff", // Dot border color
      strokeWidth: 1, // Dot border width
      size: 0,
      style: "hollow",
    },
    tooltip: {
      theme: "dark",
    },
    fill: {
      type: "gradient",
      gradient: {
        shadeIntensity: 1,
        opacityFrom: 0.7,
        opacityTo: 0.9,
        stops: [0, 100],
      },
    },
    grid: {
      borderColor: "#47535E", // Color of the grid lines
      strokeDashArray: 4, // Width of the grid lines
      show: true,
    },
  });

  useEffect(() => {
    dispatch(fetchMarketChart({coinId, days:activeLable.value, jwt: localStorage.getItem("jwt")}));
  },[dispatch,coinId,activeLable]);
    
  const handleActiveLable = (item) => {
    setActiveLable(item);
  };

  return (
    <div>
      <div id="charts">
        <div className="toolbars space-x-2">
           {timeSeries.map((item) => (
            <Button
            variant={activeLable === item.lable ? "" : "outlined"}
             onClick={() => handleActiveLable(item)} key={item.lable}>
              {item.lable}
            </Button>
             ))}
        </div>
      <div className='chart-timelines' style={{ height: '550px', width: '100%' }}>
        <ReactApexChart options={options} series={series}  type="area" height={550} />
      </div>
    </div>
    </div>
  )
}

export default StockChart
