import { PayloadAction, createSlice } from "@reduxjs/toolkit";
import { channel } from "../interfaces/chat/ChannelReceivePayload";
import { ChatState } from "../interfaces/chat/ChatState";
import { message } from "../interfaces/chat/MessageReceivePayload";
import { RootState } from "./store";

const initialState: ChatState = {
  tab: "chat",
  channels: [],
  selectedChannelName: "",
  lastMessages: "[]",
  messages: [],
  isLive: false,
};

const chatSlice = createSlice({
  name: "chatSlice",
  initialState,
  reducers: {
    setTab: (state, action: PayloadAction<string>) => {
      state.tab = action.payload;
    },
    setSelectedChannelName: (state, action: PayloadAction<string>) => {
      state.selectedChannelName = action.payload;
    },
    setChannels: (state, action: PayloadAction<channel[]>) => {
      state.channels = action.payload;
      action.payload.map((channel) => {
        if (channel.last_message === null) return;
        const reference = channel.reference;

        const lastMsgMap = new Map(JSON.parse(state.lastMessages));
        // console.log(lastMsgMap);

        lastMsgMap.set(reference, channel.last_message);
        const lastMsgStr = JSON.stringify(Array.from(lastMsgMap.entries()));
        // console.log(lastMsgStr);

        state.lastMessages = lastMsgStr;
      });
    },
    setMessages: (state, action: PayloadAction<message[]>) => {
      action.payload.map((message) => {
        const reference = message.reference;

        const lastMsgMap = new Map(JSON.parse(state.lastMessages));
        lastMsgMap.set(reference, message);
        const lastMsgStr = JSON.stringify(Array.from(lastMsgMap.entries()));

        state.lastMessages = lastMsgStr;

        if (message.reference === state.selectedChannelName) {
          state.messages = [...state.messages, ...action.payload];
        }
      });
    },
    setPrevMessages: (state, action: PayloadAction<message[]>) => {
      state.messages = action.payload;
    },
  },
});

export const {
  setTab,
  setSelectedChannelName,
  setChannels,
  setMessages,
  setPrevMessages,
} = chatSlice.actions;
export const selectChatState = (state: RootState) => state.chat;
export default chatSlice;
