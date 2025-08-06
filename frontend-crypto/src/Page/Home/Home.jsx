import { useState, useRef } from "react";
import { Button, Avatar } from "@mui/material";
import { DotIcon, Cross1Icon } from "@radix-ui/react-icons";
import { MessageCircle } from "lucide-react";
import AssetTable from "./AssetTable";
import StockChart from "../StockDetails/StockChart";
import { grey } from "@mui/material/colors";

const Home = ({ auth = { user: { fullName: "User" } }, chatBot = { messages: [], loading: false } }) => {
  const [category, setCategory] = useState("all");
  const [isBotOpen, setIsBotOpen] = useState(false);
  const [inputValue, setInputValue] = useState("");
  const chatContainerRef = useRef(null);

  const handleKeyPress = (e) => {
    if (e.key === "Enter" && inputValue.trim()) {
      console.log("User input:", inputValue);
      setInputValue("");
    }
  };

  return (
    <div className="relative min-h-screen bg-gray-50 flex flex-col md:flex-row">
      
      {/* 🧠 ChatBot Floating Panel */}
      {isBotOpen && (
        <div className="fixed bottom-20 left-5 z-50 bg-slate-900 text-white w-[90vw] md:w-[25rem] h-[70vh] rounded-lg shadow-xl flex flex-col">
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
                ref={index === chatBot.messages.length - 1 ? chatContainerRef : null}
                className={`w-fit px-4 py-2 rounded-md ${
                  msg.role === "user" ? "self-end bg-slate-800" : "self-start bg-slate-700"
                }`}
              >
                {msg.role === "user" ? msg.prompt : msg.ans}
              </div>
            ))}

            {chatBot.loading && <p className="text-sm text-gray-400">Fetching data...</p>}
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
      <div className="fixed bottom-5 left-5 z-40">
        <Button
          onClick={() => setIsBotOpen(true)}
          className="bg-white rounded-full shadow-md flex items-center gap-2 hover:bg-gray-100"
        >
          <MessageCircle size={24} className="text-slate-800 -rotate-90" />
          <span className="text-slate-800 font-medium">ChatBot</span>
        </Button>
      </div>

      {/* 📈 Main Content */}
      <main className="flex-1 p-4 space-y-6 overflow-hidden">
        {/* Stock Chart */}
        <div className="bg-white rounded-lg shadow p-4">
          <StockChart />
        </div>

        {/* Coin Info */}
        <div className="bg-white rounded-lg shadow p-4 flex items-center gap-4">
          <Avatar sx={{ width: 56, height: 56 }}>
            <img
              src=" https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcR5R6mgu0T0wZOdaIZOIfE76t2fUtjBGmr5jjMyDS5UlfJ7K1W9sge3dAx16n5Jvu22qwU&usqp=CAU" alt="Etherem" />
          </Avatar>
          <div>
            <div className="flex items-center gap-2">
              <p className="font-semibold text-lg">ETH</p>
              <DotIcon className="text-gray-400" />
              <p className="text-gray-500">Ethereum</p>
            </div>
            <div className="flex items-end gap-2">
              <p className="text-2xl font-bold text-black">56,656</p>
              <p className="text-sm text-red-500 truncate max-w-[250px]">
                -176,371,318,237.578 <span className="text-xs">(-0.29803%)</span>
              </p>
            </div>
          </div>
        </div>

        {/* Asset Table */}
        <div className="bg-white rounded-lg shadow p-4">
          <div className="flex items-center gap-4 mb-4">
            <Button
              variant={category === "all" ? "contained" : "outlined"}
               sx={{
               backgroundColor: grey[800], // darker gray
               color: '#fff',
               '&:hover': {
                backgroundColor: grey[500], // even darker on hover
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
                  backgroundColor: grey[800], // darker gray
                 color: '#fff',
                  '&:hover': {
                   backgroundColor: grey[500], // even darker on hover
                   },
                  }}
              onClick={() => setCategory("top50")}
              className="rounded-full"
            >
              Top 50
            </Button>
          </div>
          <AssetTable />
        </div>
      </main>
    </div>
  );
};

export default Home;
