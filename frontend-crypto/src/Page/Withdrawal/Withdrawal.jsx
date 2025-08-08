import React, { useEffect } from 'react'
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
import { useDispatch, useSelector } from "react-redux";
import { getWithdrawalHistory } from '../../Redux/Withdrawal/Action';
import { useNavigate } from 'react-router-dom';

const Withdrawal = () => {
  const dispatch = useDispatch();
  const { wallet, withdrawal } = useSelector((store) => store);
  const navigate = useNavigate();

  useEffect(() => {
    dispatch(getWithdrawalHistory({ jwt: localStorage.getItem("jwt") }));
  }, []);

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
          {withdrawal.history.map((item) => (
            <TableRow
              key={item.id}
              hover
              style={{ cursor: "pointer" }}
              onClick={() => navigate(`/market/${item.id}`)}
            >
              <TableCell><p>{item.date.toString()}</p>
              </TableCell>
              <TableCell>Bank</TableCell>
              <TableCell>${item.amount}</TableCell>
              <TableCell align="right">{item.status}</TableCell>
            </TableRow>
          ))}
        </TableBody>
      </Table>
    </TableContainer>
    </div>
  )
}

export default Withdrawal
