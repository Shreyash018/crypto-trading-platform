import React from 'react'
import {
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Avatar,
  Paper,
} from "@mui/material";

const Portfolio = () => {
  return (
    <div>
      <h1 className='font-bold text-gray-500 text-3xl text-left mt-4'>Portfolio</h1> 
   <TableContainer>
      <Table stickyHeader>
        <TableHead>
          <TableRow>
            <TableCell><strong>Coin</strong></TableCell>
            <TableCell>Price</TableCell>
            <TableCell>Unit</TableCell>
            <TableCell>Change</TableCell>
            <TableCell>Change%</TableCell>
            <TableCell align="right">Volume</TableCell>
          </TableRow>
        </TableHead>

        <TableBody>
          {[1,1,1,1,1,1,1].map((item) => (
            <TableRow
              key={item.id}
              hover
              style={{ cursor: "pointer" }}
              onClick={() => navigate(`/market/${item.id}`)}
            >
              <TableCell>
                <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
                  <Avatar src={item.image} alt={item.symbol} />
                  <span>{item.name}</span>
                </div>
              </TableCell>
              <TableCell>Symbol</TableCell>
              <TableCell>volume</TableCell>
              <TableCell>marketcap</TableCell>
              <TableCell ></TableCell>
              <TableCell align="right">855652</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  )
}

export default Portfolio
