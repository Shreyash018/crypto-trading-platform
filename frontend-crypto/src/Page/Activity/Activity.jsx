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

const Activity = () => {
  return (
     <div>
      <h1 className='font-bold text-gray-500 text-3xl text-left mt-4'>Trading History</h1> 
   <TableContainer>
      <Table stickyHeader>
        <TableHead>
          <TableRow>
            <TableCell>Date & Time</TableCell>
            <TableCell>Trading Pair</TableCell>
            <TableCell>Buy Price</TableCell>
            <TableCell>Sell Price</TableCell>
            <TableCell>Order Type</TableCell>
            <TableCell>Profit/Loss</TableCell>
            <TableCell align="right">value</TableCell>
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
              <TableCell><p>2024/12/12</p>
              <p className='text-gray-500'>12:20:20</p>
              </TableCell>
              <TableCell>
                <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
                  <Avatar src={item.image} alt={item.symbol} />
                  <span>{item.name}</span>
                </div>
              </TableCell>
              <TableCell>$7872</TableCell>
              <TableCell>$12874</TableCell>
              <TableCell>Buy</TableCell>
              <TableCell >$23</TableCell>
              <TableCell align="right">855652</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  )
}

export default Activity
