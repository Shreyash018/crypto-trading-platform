// import React from 'react'
// import {
//   Table,
//   TableBody,
//   TableCell,
//   TableContainer,
//   TableHead,
//   TableRow,
//   Avatar,
//   Paper,
// } from "@mui/material";
// import { useNavigate } from "react-router-dom";

// const AssetTable = ({ coins, category }) => {
 
//    const navigate = useNavigate();

//   return (
//     <TableContainer
//       component={Paper}
//       style={{
//         maxHeight: category === "all" ? "74vh" : "82vh",
//         overflowY: "auto",
//       }}
//     >
//       <Table stickyHeader>
//         <TableHead>
//           <TableRow>
//             <TableCell><strong>Coin</strong></TableCell>
//             <TableCell>SYMBOL</TableCell>
//             <TableCell>VOLUME</TableCell>
//             <TableCell>MARKET CAP</TableCell>
//             <TableCell>24H</TableCell>
//             <TableCell align="right">PRICE</TableCell>
//           </TableRow>
//         </TableHead>

//         <TableBody>
//           {coins.map((item) => (
//             <TableRow
//               key={item.id}
//               hover
//               style={{ cursor: "pointer" }}
//               onClick={() => navigate(`/market/${item.id}`)}
//             >
//               <TableCell>
//                 <div style={{ display: "flex", alignItems: "center", gap: 8 }}>
//                   <Avatar src={item.image} alt={item.symbol} />
//                   <span>{item.name}</span>
//                 </div>
//               </TableCell>
//               <TableCell>{item.symbol.toUpperCase()}</TableCell>
//               <TableCell>{item.total_volume}</TableCell>
//               <TableCell>{item.market_cap}</TableCell>
//               <TableCell style={{ color: item.market_cap_change_percentage_24h < 0 ? "red" : "green" }}>
//                 {item.market_cap_change_percentage_24h}%
//               </TableCell>
//               <TableCell align="right">{item.current_price}</TableCell>
//             </TableRow>
//           ))}
//         </TableBody>
//       </Table>
//     </TableContainer>
//   );
// }

// export default AssetTable
