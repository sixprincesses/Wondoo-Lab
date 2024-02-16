import { channel } from "./ChannelReceivePayload";
import { message } from "./MessageReceivePayload";

interface ChatState {
  tab: string;
  channels: channel[];
  selectedChannelName: string;
  lastMessages: string;
  messages: message[];
  isLive: boolean;
}

export type { ChatState };
