import { useState, useRef, useEffect } from "react";
import { Button, Avatar,TablePagination  } from "@mui/material";
import { DotIcon, Cross1Icon } from "@radix-ui/react-icons";
import { MessageCircle } from "lucide-react";
import AssetTable from "./AssetTable";
import StockChart from "../StockDetails/StockChart";
import { grey } from "@mui/material/colors";
import { useDispatch } from "react-redux";
import { fetchCoinList } from "../../Redux/Coin/Action";
import { useSelector } from "react-redux";
import { getTop50CoinList } from "../../Redux/Coin/Action";

const Home = ({
  auth = { user: { fullName: "User" } },
  chatBot = { messages: [], loading: false },
}) => {
  const [category, setCategory] = useState("all");
  const [isBotOpen, setIsBotOpen] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const chatContainerRef = useRef(null);
  const dispatch = useDispatch();

  const {coin} = useSelector(store => store);
  

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && inputValue.trim()) {
      console.log("User input:", inputValue);
      setInputValue("");
    }
  };

   useEffect(() => {
    dispatch(fetchCoinList(1));
   },[])

  useEffect(() => {
    dispatch(getTop50CoinList());
  },[category])


  const [page, setPage] = useState(0);
  const [rowsPerPage, setRowsPerPage] = useState(5);

  const handleChangePage = (event, newPage) => {
    setPage(newPage);
  };




  return (
    <div className="min-h-screen bg-gray-100 flex flex-col">
      {/* 🧠 ChatBot Floating Panel */}
      {isBotOpen && (
        <div className="fixed bottom-24 left-4 z-50 bg-slate-900 text-white w-[90vw] md:w-[25rem] h-[70vh] rounded-lg shadow-xl flex flex-col">
          {/* Header */}
          <div className="flex justify-between items-center border-b border-gray-700 px-4 py-3">
            <h2 className="font-semibold text-lg">Chat Bot</h2>
            <button onClick={() => setIsBotOpen(false)}>
              <Cross1Icon className="text-white" />
            </button>
          </div>

          {/* Messages */}
          <div className="flex-1 overflow-y-auto p-4 space-y-4">
            <div className="self-start bg-slate-800 px-4 py-2 rounded-md w-fit">
              <p>Hello, {auth.user?.fullName}</p>
              <p>You can ask crypto-related questions like:</p>
              <ul className="list-disc ml-5 text-sm">
                <li>Prices</li>
                <li>Market cap</li>
                <li>Predictions</li>
              </ul>
            </div>

            {chatBot.messages.map((msg, index) => (
              <div
                key={index}
                ref={
                  index === chatBot.messages.length - 1
                    ? chatContainerRef
                    : null
                }
                className={`w-fit px-4 py-2 rounded-md ${
                  msg.role === "user"
                    ? "self-end bg-slate-800"
                    : "self-start bg-slate-700"
                }`}
              >
                {msg.role === "user" ? msg.prompt : msg.ans}
              </div>
            ))}

            {chatBot.loading && (
              <p className="text-sm text-gray-400">Fetching data...</p>
            )}
          </div>

          {/* Input */}
          <div className="border-t border-gray-700 p-2">
            <input
              className="w-full p-2 bg-transparent border border-gray-600 rounded-md outline-none"
              placeholder="Ask something..."
              value={inputValue}
              onChange={(e) => setInputValue(e.target.value)}
              onKeyDown={handleKeyPress}
            />
          </div>
        </div>
      )}

      {/* 💬 Chatbot Toggle Button */}
      <div className="fixed bottom-6 left-6 z-40">
        <Button
          onClick={() => setIsBotOpen(true)}
          className="bg-white rounded-full shadow-md flex items-center gap-2 hover:bg-gray-100"
        >
          <MessageCircle size={24} className="text-slate-800 -rotate-90" />
          <span className="text-slate-800 font-medium">ChatBot</span>
        </Button>
      </div>

      {/* 📈 Main Content */}
      <main className="flex-1 container mx-auto px-4 py-6 space-y-6">
        {/* Stock Chart */}
        <div className="bg-white rounded-lg shadow p-4 overflow-hidden">
          <StockChart coinId={"bitcoin"} />
        </div>

        {/* Coin Info */}
        <div className="bg-white rounded-lg shadow p-4 flex flex-col sm:flex-row sm:items-center gap-4">
          <Avatar sx={{ width: 56, height: 56 }}>
            <img
              src={coin.coinList[0]?.image}
              alt="bitcoin"
            />
          </Avatar>
          <div>
            <div className="flex items-center gap-2">
              <p className="font-semibold text-lg">BTC</p>
              <DotIcon className="text-gray-400" />
              <p className="text-gray-500">Bitcoin</p>
            </div>
            <div className="flex items-end gap-2 flex-wrap">
              <p className="text-2xl font-bold text-black">${coin.coinList[0]?.current_price}</p>
              <p className="text-sm text-red-500 truncate max-w-[250px]">
                -176,371,318,237.578{" "}
                <span className="text-xs">{coin.coinList[0]?.market_cap}</span>
              </p>
            </div>
          </div>
        </div>

        {/* Asset Table */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="flex items-center gap-4 mb-4 flex-wrap">
            <Button
              variant={category === "all" ? "contained" : "outlined"}
              sx={{
                backgroundColor: grey[800],
                color: "#fff",
                "&:hover": {
                  backgroundColor: grey[500],
                },
              }}
              onClick={() => setCategory("all")}
              className="rounded-full"
            >
              All
            </Button>
            <Button
              variant={category === "top50" ? "contained" : "outlined"}
              sx={{
                backgroundColor: grey[800],
                color: "#fff",
                "&:hover": {
                  backgroundColor: grey[500],
                },
              }}
              onClick={() => setCategory("top50")}
              className="rounded-full"
            >
              Top 50
            </Button>
          </div>
          <AssetTable coin={category=="all"?coin.coinList : coin.top50} category={category}/>
          <TablePagination
           component="div"
           count="30"       // total number of items
           page={page}               // current page index (0-based)
          onPageChange={handleChangePage}
          rowsPerPage={rowsPerPage} // fixed rows per page
          rowsPerPageOptions={[]}   // ✅ hide dropdown
          labelRowsPerPage=""       // ✅ hide label text
/>
        </div>
      </main>
    </div>
  );
};

export default Home;
