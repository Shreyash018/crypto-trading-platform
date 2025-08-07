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

const Withdrawal = () => {
  return (
        <div>
      <h1 className='font-bold text-gray-500 text-3xl text-left mt-4'>Withdrawal</h1> 
   <TableContainer>
      <Table stickyHeader>
        <TableHead>
          <TableRow>
            <TableCell>Date</TableCell>
            <TableCell>Method</TableCell>
            <TableCell>Amount</TableCell>
            <TableCell align="right">Status</TableCell>
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
              </TableCell>
              <TableCell>Bank</TableCell>
              <TableCell>$12874</TableCell>
              <TableCell align="right">855652</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  )
}

export default Withdrawal
