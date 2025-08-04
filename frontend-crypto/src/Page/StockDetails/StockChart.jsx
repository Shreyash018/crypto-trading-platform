import React, {useState, useEffect} from 'react'
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

const StockChart = () => {

    const series = [
    {
      data: [
        [1751695420331, 108170.04579872],
    [1751698916373, 108268.476996074],
    [1751702606207, 108043.830268635],
    [1751706087258, 108026.862226147],
    [1751709833677, 108099.400890579],
    [1751713419581, 108159.840705043],
    [1751717029684, 108174.644336431],
    [1751720620496, 108158.506028589],
    [1751724208927, 108144.877885009],
    [1751727831772, 108203.390743806],
    [1751731408718, 108079.873202031],
    [1751735051791, 108094.121120687],
    [1751738484124, 108092.793623603],
    [1751742229277, 108029.001252804],
    [1751745845024, 108146.479974962],
    [1751749405719, 108114.236164421],
    [1751753016417, 108106.831669393],
    [1751756630380, 108207.506728376],
    [1751760237122, 108216.016211226],
    [1751763725859, 108221.01364557],
    [1751767395048, 108188.974210277],
    [1751771032271, 108135.701881406],
    [1751774627687, 108062.448521692],
    [1751778045097, 108009.167694304],
    [1751781812460, 108023.561215888],
    [1751785313067, 108153.627291759],
    [1751788872032, 108066.628754966],
    [1751792623601, 108018.476178396],
    [1751796050016, 107924.371451275],
    [1751799729500, 108014.152650592],
    [1751803431499, 108079.788275791],
    [1751807023969, 108226.77823783],
    [1751810644610, 108852.242818095],
    [1751814049792, 108881.002858702],
    [1751817822914, 108842.004636078],
    [1751821451508, 108873.266827112],
    [1751825034782, 108922.671959792],
    [1751828641993, 108465.209613024],
    [1751832234195, 108519.488300476],
    [1751835840385, 108725.750667958],
    [1751839381563, 109190.078100387],
      ],
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


  return (
    <div>
      <div id="charts">
        <div className="toolbars space-x-2">
          {timeSeries.map((item) => (
            <Button key={item.lable}>
              {item.lable}
            </Button>
          ))}
        </div>
      <div className='chart-timelines' style={{ height: '350px', width: '100%' }}>
        <ReactApexChart options={options} series={series}  type="area" height={550} />
      </div>
    </div>
    </div>
  )
}

export default StockChart
